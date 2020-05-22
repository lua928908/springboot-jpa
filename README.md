성능 최적화 사례 1)
예를들어 API를 만들때, 주문조회를 위해 order를 조회하면 결과물이 10개가 나왔다. 10개의 엔티티를 DTO로 반환할때(api스펙 변경 방지 및 엔티티 전체를 노출하는게 안좋으니까) getName() 혹은 getAddress()를 할때
프록시 대신 실제엔티티 값을 가져와야 하기 때문에 쿼리를 날리는데 n+1 문제가 발생한다. 이걸 막기위해 fetch join을 해주면 21방 나가던 쿼리가 한번에 가져와 지는 최적화가 이루어 진다.

Hibernate: 
    select
        order0_.order_id as order_id1_6_0_,
        member1_.member_id as member_i1_4_1_,
        delivery2_.delivery_id as delivery1_2_2_,
        order0_.delevery_id as delevery4_6_0_,
        order0_.member_id as member_i5_6_0_,
        order0_.order_date as order_da2_6_0_,
        order0_.status as status3_6_0_,
        member1_.city as city2_4_1_,
        member1_.street as street3_4_1_,
        member1_.zipcode as zipcode4_4_1_,
        member1_.name as name5_4_1_,
        delivery2_.city as city2_2_2_,
        delivery2_.street as street3_2_2_,
        delivery2_.zipcode as zipcode4_2_2_,
        delivery2_.status as status5_2_2_ 
    from
        orders order0_ 
    inner join
        member member1_ 
            on order0_.member_id=member1_.member_id 
    inner join
        delivery delivery2_ 
            on order0_.delevery_id=delivery2_.delivery_id

이런 쿼리가 나간다, order와 멤버를 조인하고 다시 딜리버리와 on으로 조인을 하고 조인한 애를 select 절로 풀어주게 되는데 이걸 직접 쿼리로 작성하려면 시간이 많이걸린다.
추상적 개념만 제공하면 실질적인 쿼리는 하이버네이트와 같은 실체가 만들어준다.

@GetMapping("/api/v3/orders")
public List<OrderDto> ordersV3(){
    List<Order> orders = orderRepository.findAllWithItem();
    List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());
    return result;
}
위 처럼 order 레포지터리 에서 findAllWithItem을 할 때
public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class)
                .getResultList();
    }
단지 JPQL 쿼리에 join fetch를 넣어주는것 만으로 distinct까지 고려한 성능최적화가 이루어 지는 것이다.
여러방 나가던 쿼리가 1개로 축소되는 효과를 단 몇분이면 처리하는게 가능하다, 추상적 개념만 전달하면 노가다를 통해 바꾸어야 할 코드를 구현체(하이버네이트)가 알아서 해주기 때문이다.
이런 부분은 실무에서 엄청난 효과라고 생각한다.
위 상황은 1대다 조인이기 때문에 distinct를 추가하였다, 다대1 혹은 1대1 관계라면 distinct는 필요없다.

* 치명적 단점 - 페이징 불가능, 페이징 처리가 안된다.
em.createQuery(query)
.setFirstResult(1)
.setMaxResults(100)
이게 불가능해 진다는 뜻이다.
fetch join과 페이징쿼리를 같이쓰게되면 메모리에서 페이징처리를 하게된다, 그래서 경고를 내게된다. 만약 결과물이 1만개 라면 메모리에 결과물 1만개를 올리고 정렬을 하게된다.
fetch join을 하고난 결과물이 뻥튀기된다(1대다 라는 가정하에) 그상태에서 페이징관련 메서드를 사용하면 db의 쿼리를 통해 결과물이 뻥튀기 되므로 페이징을 어떻게 정렬할것 인가 하는 기준이 틀어지게된다.
그래서 1대다 관계(다대다는 쓰면안된다)에서 fetch join으로 join을 한다 라는 조건에서는 페이징이 안된다.



코드의 단축)

repository를 인터페이스로 만들고 JpaRepository를 상속받으면 자동으로 CRUD와 기타 중복이 발생할만한 기본 메서드들이 다 공짜로 생기게 되는 것이다, 단순히 임플리먼츠 하는것 만으로 코드의 양을 훨씬 줄이고
개발자는 비즈니스 로직 자체에 집중할 수 있게 된다. 기본 메서드에 없는 것들은 인터페이스에서 작성해주면된다, 그런데 놀라운것은 인터페이스에 명시적으로 메서드를 작성했으면 implement에서 구현을 해주어야
하는데 자동으로 생긴다는 점이다. 단 findByname이라고 쳤을때 findBy 이후에 나온 이름(name)을 가지고 자동으로 구현해주는것이다. (select * from Member m where m.name=? ) 와 같은 형태로 만들어준다.
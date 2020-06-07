package jpabook.jpashop.repository;

import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUseranmeList();

    @Query("select new jpabook.jpashop.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건 조회
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    Page<Member> findByAge(int age, Pageable pageable); // 페이징 쿼리가 복잡한경우 @Query(value = "", countQuery = ) 를 통해 카운트용 쿼리를 만들어서 성능을 올려줄 수 있다.

    /*
        변경이 발생하는 쿼리라는걸 알려주는 어노테이션이 꼭 필요함
        벌크 수정이후에는 꼭 클리어로 영속성 컨텍스트를 비워야 한다.
        벌크연산을 할 때 영속성컨텍스트는 DB가 변경되었음을 모르기 때문이다.
    */
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
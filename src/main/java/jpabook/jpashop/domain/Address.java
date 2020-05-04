package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙상 만든 생성자
    protected Address() {
    }

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}


/*
    값 타입은 변경 불가능하게 설계해야 한다. 그래서 @Setter를 안만든다.
    JPA 스펙상 엔티티나 엠베디드 타입은 기본생성자를 public 또는 protected로 설정해야 한다.
    JPA가 이런 제약을 두는 이유는 JPA 구현 라이브러리(하이버네이트)가 객체를 생성할 때 리플렉션같은 기술을
    사용할 수 있도록 지원해야 하기 때문이다.
*/
package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}

/*
    MemberForm을 굳이 왜만들어야 하나 싶기도 했으나,
    Member Entity와 내용이 비슷하지만 그렇다고 member 엔티티를 받아와서 쓰면 안된다,
    정말 단순한 서비스라면 요구사항이 단순해 상관없을 수 있겠으나 실제로는 그렇게 단순한 경우가 없다
    MemberForm을 만드는게 비효율 적이라고 해서 멤버 엔티티를 뷰를 위한 값을 주고받는 용도로 사용해버리면
    유지보수에 문제가 생긴다.
    엔티티는 최대한 순수하게 유지 해야한다. 정말 필요한 핵심로직만을 가지고 있게 유지해야 하는데
    view를 위해 엔티티에 @NotNull과 같은 부수적인 내용들이 들어가기 시작하면 어플리케이션이 확장되어
    덩치가 커질때 유지보수에 문제가 생긴다.

    핵심 비즈니스 로직에 화면과 관련된 기능이 들어가면 안된다.
*/
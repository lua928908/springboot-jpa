package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}

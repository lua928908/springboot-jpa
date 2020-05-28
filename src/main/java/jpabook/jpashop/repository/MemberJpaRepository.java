package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }


    public Member find(Long id){
        return em.find(Member.class, id);
    }
}

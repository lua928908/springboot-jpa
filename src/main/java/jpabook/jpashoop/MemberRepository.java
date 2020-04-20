package jpabook.jpashoop;

import jpabook.jpashoop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository // 컴포넌트 스캔의 대상이 되는 어노테이션, 자동으로 스프링 bean에 등록
public class MemberRepository {
    @PersistenceContext // spring-boot-data-jpa 에서 알아서 엔티티팩토리를 통해 엔티티매니저를 만들어줌
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); // 가급적 사이드 이펙트를 일으킬 커맨드성을 막기위해 멤버를 리턴하는대신 id만 조회할 수 있게 설계
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}

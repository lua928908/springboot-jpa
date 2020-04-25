package jpabook.jpashoop.repository;

import jpabook.jpashoop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 컴포넌트 스캔의 대상이 되는 어노테이션, 자동으로 스프링 bean에 등록
public class MemberRepository {
    @PersistenceContext // spring-boot-data-jpa 에서 알아서 엔티티팩토리를 통해 엔티티매니저를 만들고 주입해줌
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); // 가급적 사이드 이펙트를 일으킬 커맨드성을 막기위해 멤버를 리턴하는대신 id만 조회할 수 있게 설계
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name) // 쿼리에 name을 인자 name과 매칭
                .getResultList();
    }
}

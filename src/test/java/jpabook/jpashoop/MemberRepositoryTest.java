package jpabook.jpashoop;

import jpabook.jpashoop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepositry;

    @Test
    @Transactional // 트랜잭션이 테스트에 들어있으면 마지막에 커밋할 때 데이터가 롤백됨, java 표준 트랜잭션도있고, 스프링이 제공하는 트랜잭션도 있는데 스프링을 쓰는게 낫다 (어차피 이 어플리케이션이 스프링종속적이고 추가로 사용할 수 있는 옵션도 있기 때문에)
    @Rollback(false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long saveId = memberRepositry.save(member);
        Member findMember = memberRepositry.find(saveId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}
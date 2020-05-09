package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회만 하는 메서드인 경우(읽기전용) readOnly옵션을 주면 성능최적화가 된다, 더티체킹을 안하는 등의 최적화
@RequiredArgsConstructor // final로 정의된 필드에 생성자 메서드를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional // 변경이 있는경우 트랜잭션만 달아주면 readOnly가 기본 false여서 쓰기도 가능해짐
    // 회원가입
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findName(member.getName()); // member에 name을 유니크 제약조건을 하는것이 좋다. 멀티쓰레드로 인해 같은이름이 동시에 들어갈 수도 있음
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

/*
    @Autowired를 통해 주입을 받을 때 필드로 바로 받아서 사용하면 편하긴 하지만 테스트코드를 작성할 때 Mock을 사용하기가 쉽지않다.
    그런이유로 contructor로 생성될때 주입을 받아 this.examRepository 와 같은식으로 넣어주는것을 권장한다.
    즉, 테스트코드나 다른 개입을 위해 필드로 주입받는 것 보다 생성자에서 주입받는것을 권장
*/
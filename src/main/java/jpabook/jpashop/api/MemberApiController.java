package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse savememberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest{
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}

/*
    엔티티를 파라미터로 받으면 안된다, 나중에 엔티티가 변경되게 되었을때 API의 스펙자체가 변해버리면
    프론트에서 장애가 생긴다. 엔티티명을 바꾸거나 필드를 추가할때 생기는 사이드이펙트가 예상이 안된다
    이런상황을 막기위해 파라미터로 받은 클라이언트의 요청을 별도의 Dto로 갈아끼워서
    Dto를 가지고 멤버를 생성해야 한다.
    Dto를 통해 api로 받는 파라미터가 무엇인지 알 수 있고 NotEmpty 같은 설정을 api별로 지정할 수 있다.
*/
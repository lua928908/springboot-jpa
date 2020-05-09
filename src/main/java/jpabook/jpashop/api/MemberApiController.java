package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName()); // 01. 여기서 member엔티티를 반환하지 않았다, 반환해주면 영속성 컨텍스트와 연결이 끊긴채로 엔티티만 반환하게 되는데 이상태로 사용하는게 위험하기 때문이다.
        Member findMember = memberService.findOne(id); // 02. 여기서 다시 조회해왔다, 영속성 컨텍스트에 관리될 수 있도록 (쿼리와 커맨드를 분리하기 위함도 있다.)
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
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
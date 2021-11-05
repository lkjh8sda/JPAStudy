package jpabook.jpashop.api;

import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

//    @PostMapping("/api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
//        return "ok";
//    }

    @Data
    static class CreateMemberResponse{
        private Long id;
    }
}

package weavers.siltarae.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weavers.siltarae.login.Auth;
import weavers.siltarae.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@Auth Long memberId) {
        memberService.deleteMember(memberId);

        return ResponseEntity.noContent().build();
    }

}
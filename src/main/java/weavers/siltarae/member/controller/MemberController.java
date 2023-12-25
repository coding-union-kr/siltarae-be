package weavers.siltarae.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.login.Auth;
import weavers.siltarae.member.dto.MemberResponse;
import weavers.siltarae.member.dto.MemberUpdateRequest;
import weavers.siltarae.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PutMapping
    public ResponseEntity<MemberResponse> updateMember(@Auth Long memberId, @RequestBody MemberUpdateRequest request) {
        MemberResponse response = memberService.updateMember(memberId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
            @Auth Long memberId,
            @CookieValue("refresh-token") final String refreshToken) {
        memberService.deleteMember(memberId, refreshToken);

        return ResponseEntity.noContent().build();
    }

}

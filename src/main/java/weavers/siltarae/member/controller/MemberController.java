package weavers.siltarae.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weavers.siltarae.login.Auth;
import weavers.siltarae.member.dto.MemberResponse;
import weavers.siltarae.member.dto.MemberUpdateRequest;
import weavers.siltarae.member.service.MemberService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/image")
    public ResponseEntity<Void> uploadMemberImage(@Auth Long memberId, @RequestPart MultipartFile file) {
        memberService.deleteMemberImage(memberId);
        String imageUrl = memberService.uploadMemberImage(memberId, file);

        return ResponseEntity.created(URI.create(imageUrl)).build();
    }

    @PutMapping
    public ResponseEntity<MemberResponse> updateMemberNickname(@Auth Long memberId, @RequestBody MemberUpdateRequest request) {
        MemberResponse response = memberService.changeMemberNickname(memberId, request);

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

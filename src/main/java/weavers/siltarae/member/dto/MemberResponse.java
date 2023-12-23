package weavers.siltarae.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String nickname;

    @Builder
    public MemberResponse(String nickname) {
        this.nickname = nickname;
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .nickname(member.getNickname())
                .build();
    }
}

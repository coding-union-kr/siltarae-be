package weavers.siltarae.member.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNicknameResponse {

    private String nickname;

    @Builder
    public MemberNicknameResponse(String nickname) {
        this.nickname = nickname;
    }

    public static MemberNicknameResponse from(Member member) {
        return MemberNicknameResponse.builder()
                .nickname(member.getNickname())
                .build();
    }
}

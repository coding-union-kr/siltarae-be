package weavers.siltarae.member.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoResponse {

    private String nickname;
    private String imageUrl;

    @Builder
    public MemberInfoResponse(String nickname, String imageUrl) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .build();
    }
}

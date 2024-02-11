package weavers.siltarae.login.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import weavers.siltarae.login.dto.response.TokenPair;
import weavers.siltarae.member.domain.Member;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInfo {

    private Long memberId;
    private String nickname;
    private String profileImage;
    private String accessToken;
    private String refreshToken;

    private Long refreshTokenExpirationTime;

    @Builder
    public LoginInfo(Long memberId, String nickname, String profileImage, String accessToken, String refreshToken, Long refreshTokenExpirationTime) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    public static LoginInfo from(Member member, TokenPair tokenPair) {
        return LoginInfo.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileImage(member.getImageUrl())
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .refreshTokenExpirationTime(tokenPair.getRefreshTokenExpirationTime())
                .build();
    }
}

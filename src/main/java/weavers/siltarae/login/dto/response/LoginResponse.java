package weavers.siltarae.login.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.login.domain.LoginInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {
    private Long memberId;
    private String nickname;
    private String profileImage;
    private String accessToken;

    @Builder
    public LoginResponse(Long memberId, String nickname, String profileImage, String accessToken) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.accessToken = accessToken;
    }

    public static LoginResponse from (LoginInfo loginInfo) {
        return LoginResponse.builder()
                .memberId(loginInfo.getMemberId())
                .nickname(loginInfo.getNickname())
                .profileImage(loginInfo.getProfileImage())
                .accessToken(loginInfo.getAccessToken())
                .build();
    }
}

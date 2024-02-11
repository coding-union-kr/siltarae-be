package weavers.siltarae.login.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenPair {

    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

    @Builder
    public TokenPair(String accessToken, String refreshToken, Long refreshTokenExpirationTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}

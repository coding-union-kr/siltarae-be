package weavers.siltarae.login.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResponse {
    private String accessToken;

    @Builder
    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

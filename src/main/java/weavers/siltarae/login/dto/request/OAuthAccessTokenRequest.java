package weavers.siltarae.login.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthAccessTokenRequest {

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    private String grantType;

    private String code;

    @Builder
    public OAuthAccessTokenRequest(String clientId, String clientSecret, String redirectUri, String grantType, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.grantType = grantType;
        this.code = code;
    }
}

package weavers.siltarae.login.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import weavers.siltarae.login.dto.request.OAuthAccessTokenRequest;
import weavers.siltarae.login.dto.response.OAuthAccessTokenResponse;
import weavers.siltarae.login.dto.response.UserInfoResponse;
import weavers.siltarae.user.domain.SocialType;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleProvider extends OAuthProvider {

    private final GoogleAuthClient googleAuthClient;

    private final String SOCIAL_TYPE = "GOOGLE";

    @Value("${oauth2.google.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.google.client-secret}")
    private String CLIENT_SECRET;

    @Value("${oauth2.google.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${oauth2.google.grant-type}")
    private String GRANT_TYPE;

    @Value("${oauth2.google.userinfo-base-uri}")
    private String USERINFO_BASE_URI;

    @Override
    public UserInfoResponse getUserInfo(String accessToken) {

        ResponseEntity<UserInfoResponse> response = googleAuthClient.getUserInfo(URI.create(USERINFO_BASE_URI), accessToken);

        UserInfoResponse userInfo = response.getBody();
        userInfo.setSocialType(SocialType.GOOGLE);
        // TODO 응답코드 체크

        return userInfo;
    }

    @Override
    public String requestAccessToken(final String code) {

        OAuthAccessTokenRequest request = OAuthAccessTokenRequest.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .code(code).build();

        ResponseEntity<OAuthAccessTokenResponse> response = googleAuthClient.getAccessToken(request);

        // TODO 응답코드 체크

        OAuthAccessTokenResponse accessTokenResponse = response.getBody();
        log.info("accessToken = {}", accessTokenResponse.getAccessToken());

        return accessTokenResponse.getAccessToken();
    }
}

package weavers.siltarae.login.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import weavers.siltarae.login.dto.request.OAuthAccessTokenRequest;
import weavers.siltarae.login.dto.response.OAuthAccessTokenResponse;
import weavers.siltarae.login.dto.response.MemberInfoResponse;
import weavers.siltarae.member.domain.SocialType;

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

    @Value("${oauth2.google.memberinfo-base-uri}")
    private String MEMBERINFO_BASE_URI;

    @Override
    public MemberInfoResponse getMemberInfo(String accessToken) {

        ResponseEntity<MemberInfoResponse> response = googleAuthClient.getMemberInfo(URI.create(MEMBERINFO_BASE_URI), accessToken);

        MemberInfoResponse memberInfo = response.getBody();
        memberInfo.setSocialType(SocialType.GOOGLE);
        // TODO 응답코드 체크

        return memberInfo;
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

        return accessTokenResponse.getAccessToken();
    }
}

package weavers.siltarae.login.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.AuthException;
import weavers.siltarae.global.exception.ExceptionCode;
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

    @Value("${oauth2.google.member-info-base-uri}")
    private String MEMBER_INFO_BASE_URI;

    @Override
    public MemberInfoResponse getMemberInfo(String accessToken) {

        ResponseEntity<MemberInfoResponse> response = googleAuthClient.getMemberInfo(URI.create(MEMBER_INFO_BASE_URI), accessToken);

        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody()) {
            throw new AuthException(ExceptionCode.NOT_SUPPORTED_AUTH_SERVICE);
        }

        MemberInfoResponse memberInfo = response.getBody();
        memberInfo.setSocialType(SocialType.GOOGLE);

        return memberInfo;
    }

    @Override
    public String requestAccessToken(final String code, final String redirectUri) {

        OAuthAccessTokenRequest request = OAuthAccessTokenRequest.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(redirectUri)
                .grantType(GRANT_TYPE)
                .code(code).build();

        ResponseEntity<OAuthAccessTokenResponse> response = googleAuthClient.getAccessToken(request);

        if(!response.getStatusCode().is2xxSuccessful() || !response.hasBody()) {
            throw new AuthException(ExceptionCode.INVALID_AUTHORIZATION_CODE);
        }

        return response.getBody().getAccessToken();
    }
}

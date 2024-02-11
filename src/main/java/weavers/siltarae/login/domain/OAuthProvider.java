package weavers.siltarae.login.domain;

import weavers.siltarae.login.dto.response.MemberInfoResponse;

public abstract class OAuthProvider {

    String SOCIAL_TYPE;

    boolean isMatched(String socialType) {
        return socialType.equals(SOCIAL_TYPE);
    }

    abstract MemberInfoResponse getMemberInfo(String accessToken);

    abstract String requestAccessToken(String code, String redirectUri);

}

package weavers.siltarae.login.infrastructure;

import weavers.siltarae.login.dto.response.MemberInfoResponse;

public interface OAuthProvider {

    boolean isMatched(String socialType);

    MemberInfoResponse getMemberInfo(String accessToken);

    String requestAccessToken(String code, String redirectUri);

}

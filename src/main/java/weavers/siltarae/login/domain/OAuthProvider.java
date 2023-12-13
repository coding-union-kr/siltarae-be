package weavers.siltarae.login.domain;

public interface OAuthProvider {

    boolean isMatched(String socialType);

    void getUserInfo(String accessToken);

    String requestAccessToken(String code);

}

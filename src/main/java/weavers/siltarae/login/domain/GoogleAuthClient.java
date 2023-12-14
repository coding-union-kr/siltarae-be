package weavers.siltarae.login.domain;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import weavers.siltarae.login.dto.request.OAuthAccessTokenRequest;
import weavers.siltarae.login.dto.response.OAuthAccessTokenResponse;
import weavers.siltarae.login.dto.response.UserInfoResponse;

import java.net.URI;

@FeignClient(name = "googleAuthClient", url = "${oauth2.google.token-base-uri}")
public interface GoogleAuthClient {

    @PostMapping("/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResponseEntity<OAuthAccessTokenResponse> getAccessToken(@RequestBody OAuthAccessTokenRequest request);

    @GetMapping("/oauth2/v1/userinfo")
    ResponseEntity<UserInfoResponse> getUserInfo(URI baseUri, @RequestParam("access_token") String accessToken);
}

package weavers.siltarae.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.login.Auth;
import weavers.siltarae.login.dto.request.LoginRequest;
import weavers.siltarae.login.dto.response.AccessTokenResponse;
import weavers.siltarae.login.domain.LoginInfo;
import weavers.siltarae.login.dto.response.LoginResponse;
import weavers.siltarae.login.service.LoginService;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    private final String REFRESH_TOKEN = "refresh-token";

    @PostMapping("/login/{socialType}")
    public ResponseEntity<LoginResponse> login(@PathVariable final String socialType,
                                               @RequestBody final LoginRequest request) {

        LoginInfo loginInfo = loginService.login(request.getAuthCode(), request.getRedirectUri());

        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, loginInfo.getRefreshToken())
                .maxAge(loginInfo.getRefreshTokenExpirationTime())
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();

        LoginResponse response = LoginResponse.from(loginInfo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(SET_COOKIE, cookie.toString())
                .body(response);
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @RequestHeader("Authorization") final String authorizationHeader,
            @CookieValue("refresh-token") final String refreshToken) {

        AccessTokenResponse accessTokenResponse = loginService.renewAccessToken(authorizationHeader, refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accessTokenResponse);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(
            @Auth final Long memberId,
            @CookieValue("refresh-token") final String refreshToken) {
        loginService.logout(refreshToken);

        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, null)
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.noContent()
                .header(SET_COOKIE, cookie.toString())
                .build();
    }

}

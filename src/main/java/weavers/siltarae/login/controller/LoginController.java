package weavers.siltarae.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.login.dto.response.AccessTokenResponse;
import weavers.siltarae.login.dto.response.TokenPair;
import weavers.siltarae.login.service.LoginService;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/{socialType}")
    public ResponseEntity<AccessTokenResponse> login(@PathVariable final String socialType,
                                                     @RequestParam final String code) {

        final TokenPair tokenPair = loginService.login(socialType, code);

        ResponseCookie cookie = ResponseCookie.from("refresh-token", tokenPair.getRefreshToken())
                .maxAge(tokenPair.getRefreshTokenExpirationTime())
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();

        AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
                .accessToken(tokenPair.getAccessToken())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(SET_COOKIE, cookie.toString())
                .body(accessTokenResponse);
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
    public ResponseEntity<Void> logout(@CookieValue("refresh-token") final String refreshToken) {
        loginService.logout(refreshToken);

        return ResponseEntity.noContent().build();
    }

}

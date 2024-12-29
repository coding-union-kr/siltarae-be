package weavers.siltarae.global.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import weavers.siltarae.login.AccessTokenExtractor;
import weavers.siltarae.login.infrastructure.TokenProvider;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AccessTokenExtractor accessTokenExtractor;
    private final AuthContext authContext;
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String accessToken = accessTokenExtractor.extractAccessToken(authorizationHeader);
        Long memberId = tokenProvider.getMemberIdFromAccessToken(accessToken);
        authContext.setMemberId(memberId);

        return true;
    }
}

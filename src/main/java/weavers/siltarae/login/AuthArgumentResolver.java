package weavers.siltarae.login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import weavers.siltarae.login.domain.TokenProvider;

import static org.springframework.http.HttpHeaders.*;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AccessTokenExtractor accessTokenExtractor;
    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.hasParameterAnnotation(Auth.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasAuthAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = accessTokenExtractor.extractAccessToken(request.getHeader(AUTHORIZATION));

        return tokenProvider.getMemberIdFromAccessToken(accessToken);
    }
}

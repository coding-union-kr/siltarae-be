package weavers.siltarae.login;

import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.AuthException;
import weavers.siltarae.global.exception.ExceptionCode;

@Component
public class AccessTokenExtractor {

    private final String BEARER_TYPE = "Bearer";

    public String extractAccessToken(String authorizationHeader) {
        if(authorizationHeader==null || !authorizationHeader.startsWith(BEARER_TYPE)) {
            throw new AuthException(ExceptionCode.AVAILABLE_AFTER_LOGIN);
        }
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }


}

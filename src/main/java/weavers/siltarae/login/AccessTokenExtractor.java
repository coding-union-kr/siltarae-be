package weavers.siltarae.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.AuthException;
import weavers.siltarae.global.exception.ExceptionCode;

@Slf4j
@Component
public class AccessTokenExtractor {

    private final String BEARER_TYPE = "Bearer";

    public String extractAccessToken(String authorizationHeader) {
        if(authorizationHeader==null || !authorizationHeader.startsWith(BEARER_TYPE)) {
            log.info("=========== authorization header = {} ==========", authorizationHeader);
            throw new AuthException(ExceptionCode.AVAILABLE_AFTER_LOGIN);
        }
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }


}

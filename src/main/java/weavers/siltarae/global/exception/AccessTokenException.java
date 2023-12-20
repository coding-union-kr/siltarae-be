package weavers.siltarae.global.exception;

import lombok.Getter;

@Getter
public class AccessTokenException extends AuthException{

    public AccessTokenException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
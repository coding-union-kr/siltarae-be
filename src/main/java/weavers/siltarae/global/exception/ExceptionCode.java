package weavers.siltarae.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    INVALID_MISTAKE_CONTENT_SIZE(2001, "실수 내용은 140자가 넘을 수 없습니다."),
    INVALID_MISTAKE_CONTENT_NULL(2002, "실수 내용은 필수입니다."),

    INTERNAL_SEVER_ERROR(9999, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int code;
    private final String message;
}

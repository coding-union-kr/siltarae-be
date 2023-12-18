package weavers.siltarae.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    DUPLICATED_TAG_NAME(3001, "중복된 태그명입니다."),

    INVALID_MISTAKE_CONTENT_SIZE(2001, "실수 내용은 140자가 넘을 수 없습니다."),
    INVALID_MISTAKE_CONTENT_NULL(2002, "실수 내용은 필수입니다."),
    INVALID_MISTAKE_CONTENT(2003, "실수 내용에는 한글, 영어, 숫자만 허용됩니다."),
    NOT_FOUND_MISTAKE(2004, "해당하는 실수가 존재하지 않습니다."),

    NOT_FOUND_TAG(3002, "해당하는 태그가 존재하지 않습니다."),

    NOT_FOUND_MEMBER(4001, "해당하는 회원이 존재하지 않습니다."),

    NOT_SUPPORTED_AUTH_SERVICE(5001, "지원하는 소셜 로그인 서비스가 아닙니다."),
    INVALID_AUTHORIZATION_CODE(5002, "인증 코드가 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(5101, "만료된 액세스 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(5102, "만료된 리프레시 토큰입니다."),
    INVALID_ACCESS_TOKEN(5103, "유효하지 않은 액세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(5104, "유효하지 않은 리프레시 토큰입니다."),
    FAIL_TO_VALIDATE_TOKEN(5105, "토큰 유효성 검사에 실패했습니다."),


    INTERNAL_SEVER_ERROR(9999, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int code;
    private final String message;
}

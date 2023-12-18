package weavers.siltarae.login.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    private String authCode;

    @Builder
    public LoginRequest(String authCode) {
        this.authCode = authCode;
    }
}

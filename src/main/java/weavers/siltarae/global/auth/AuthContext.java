package weavers.siltarae.global.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {
    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}

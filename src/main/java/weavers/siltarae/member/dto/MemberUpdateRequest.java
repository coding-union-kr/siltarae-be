package weavers.siltarae.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequest {

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Length(max = 10, message = "닉네임은 최대 10자입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 한글, 영어, 숫자만 사용 가능합니다.")
    private String nickname;

    @Builder
    public MemberUpdateRequest(String nickname) {
        this.nickname = nickname;
    }
}

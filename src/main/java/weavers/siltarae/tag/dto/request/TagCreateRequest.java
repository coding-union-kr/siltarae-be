package weavers.siltarae.tag.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagCreateRequest {

    @NotEmpty(message = "태그명을 입력해주세요.")
    @Length(max = 10, message = "태그명은 최대 10자입니다.")
    @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣_]*$", message = "태그명에는 한글, 영어, 숫자, _만 사용 가능합니다.")
    private String name;

    @Builder
    public TagCreateRequest(String name) {
        this.name = name;
    }
}

package weavers.siltarae.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentCreateRequest {
    @NotNull(message = "실수는 필수 입력값 입니다.")
    private Long mistakeId;
    @NotBlank(message = "댓글 내용은 필수 입니다.")
    @Size(max = 140, message = "댓글 내용은 140자를 넘을 수 없습니다.")
    private String content;
}

package weavers.siltarae.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.dto.request.PageRequest;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentListRequest extends PageRequest {
    @NotNull(message = "실수 아이디는 필수 입니다.")
    private Long mistakeId;

    public CommentListRequest(Integer page, Integer size, Long mistakeId) {
        super(page, size);
        this.mistakeId = mistakeId;
    }
}

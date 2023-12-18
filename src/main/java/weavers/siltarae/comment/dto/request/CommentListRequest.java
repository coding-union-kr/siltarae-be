package weavers.siltarae.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentListRequest {
    @NotNull(message = "페이지 번호는 필수 입니다.")
    private Integer page;
    @NotNull(message = "페이지 개수는 필수 입니다.")
    private Integer size;
    @NotNull(message = "실수 아이디는 필수 입니다.")
    private Long mistakeId;

    public CommentListRequest(Integer page, Integer size, Long mistakeId) {
        this.page = page;
        this.size = size;
        this.mistakeId = mistakeId;
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}

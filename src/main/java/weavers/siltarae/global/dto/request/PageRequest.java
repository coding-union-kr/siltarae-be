package weavers.siltarae.global.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.data.domain.PageRequest.of;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PageRequest {
    @NotNull(message = "페이지 번호는 필수 입니다.")
    private Integer page;

    @NotNull(message = "페이지 개수는 필수 입니다.")
    private Integer size;

    public Pageable toPageable() {
        return of(page, size);
    }

    public PageRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}

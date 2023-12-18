package weavers.siltarae.mistake.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FeedRequest {
    @NotNull(message = "페이지 번호는 필수 입니다.")
    private Integer page;
    @NotNull(message = "페이지 개수는 필수 입니다.")
    private Integer size;
    @NotNull(message = "피드 타입은 필수 입니다.")
    private FeedType feedType;

    public FeedRequest(Integer page, Integer size, FeedType feedType) {
        this.page = page;
        this.size = size;
        this.feedType = feedType;
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}

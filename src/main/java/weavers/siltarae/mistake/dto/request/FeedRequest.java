package weavers.siltarae.mistake.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.dto.request.PageRequest;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FeedRequest extends PageRequest {
    @NotNull(message = "피드 타입은 필수 입니다.")
    private FeedType feedType;

    public FeedRequest(Integer page, Integer size, FeedType feedType) {
        super(page, size);
        this.feedType = feedType;
    }
}

package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import weavers.siltarae.mistake.domain.Mistake;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FeedListResponse {
    private Long totalCount;
    private List<FeedResponse> feeds;

    @Builder
    public FeedListResponse(Long totalCount, List<FeedResponse> feeds) {
        this.totalCount = totalCount;
        this.feeds = feeds;
    }

    public static FeedListResponse from (Page<Mistake> mistakes) {
        List<FeedResponse> feedResponses = mistakes.stream().map(
                FeedResponse::from
        ).toList();

        return FeedListResponse.builder()
                .totalCount(mistakes.getTotalElements())
                .feeds(feedResponses)
                .build();
    }
}

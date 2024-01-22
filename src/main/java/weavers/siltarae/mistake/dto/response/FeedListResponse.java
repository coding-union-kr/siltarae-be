package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static FeedListResponse from(List<FeedResponse> feedResponses, Long totalCount) {
        return FeedListResponse.builder()
                .totalCount(totalCount)
                .feeds(feedResponses)
                .build();
    }
}

package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.tag.dto.response.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FeedResponse {
    private Long id;
    private String content;
    private Integer commentCount;
    private Integer likeCount;
    private Long memberId;
    private String memberName;

    @Builder
    public FeedResponse(Long id, String content, Integer commentCount, Integer likeCount, Long memberId, String memberName) {
        this.id = id;
        this.content = content;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    private static final Integer TEST_COMMENT_COUNT = 13;
    private static final Integer TEST_LIKE_COUNT = 11;

    public static FeedResponse from(Mistake mistake) {
        return FeedResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(TEST_COMMENT_COUNT)
                .likeCount(TEST_LIKE_COUNT)
                .build();
    }

}

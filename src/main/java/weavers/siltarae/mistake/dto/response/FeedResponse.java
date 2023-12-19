package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.mistake.domain.Mistake;

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

    public static FeedResponse from(Mistake mistake) {
        return FeedResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(mistake.getComments().size())
                .likeCount(mistake.getLikes().size())
                .build();
    }

}

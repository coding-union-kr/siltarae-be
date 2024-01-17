package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.like.service.LikeService;
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
    private Boolean likeAble;

    @Builder
    public FeedResponse(Long id, String content, Integer commentCount, Integer likeCount, Long memberId, String memberName, Boolean likeAble) {
        this.id = id;
        this.content = content;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.memberId = memberId;
        this.memberName = memberName;
        this.likeAble = likeAble;
    }

    public static FeedResponse from(Mistake mistake, Long memberId) {
        return FeedResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(mistake.getExistingCommentCount(mistake))
                .likeCount(mistake.getLikes().size())
                .memberId(mistake.getMember().getId())
                .memberName(mistake.getMember().getNickname())
                .likeAble(mistake.getLikeAbleFromMemberIdAndMistake(mistake, memberId))
                .build();
    }

}

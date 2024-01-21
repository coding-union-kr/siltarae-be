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
    private String memberImageUrl;
    private Boolean likeAble;

    @Builder
    public FeedResponse(Long id, String content, Integer commentCount, Integer likeCount, Long memberId, String memberName, Boolean likeAble, String memberImageUrl) {
        this.id = id;
        this.content = content;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.memberId = memberId;
        this.memberName = memberName;
        this.likeAble = likeAble;
        this.memberImageUrl = memberImageUrl;
    }

    public static FeedResponse from(Mistake mistake, Boolean likeAble) {
        return FeedResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(mistake.getExistingCommentCount(mistake))
                .likeCount(mistake.getLikes().size())
                .memberId(mistake.getMember().getId())
                .memberName(mistake.getMember().getNickname())
                .memberImageUrl(mistake.getMember().getImageUrl())
                .likeAble(likeAble)
                .build();
    }

}

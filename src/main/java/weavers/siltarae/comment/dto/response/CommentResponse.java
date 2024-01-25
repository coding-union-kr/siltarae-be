package weavers.siltarae.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.comment.domain.Comment;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentResponse {
    private Long memberId;
    private String memberName;
    private String memberImageUrl;
    private Long commentId;
    private String commentContent;

    @Builder
    public CommentResponse(Long memberId, String memberName, Long commentId, String commentContent, String memberImageUrl) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.memberImageUrl = memberImageUrl;
    }

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .commentContent(comment.getContent())
                .memberId(comment.getMember().getId())
                .memberName(comment.getMember().getNickname())
                .memberImageUrl(comment.getMember().getImageUrl())
                .build();
    }
}

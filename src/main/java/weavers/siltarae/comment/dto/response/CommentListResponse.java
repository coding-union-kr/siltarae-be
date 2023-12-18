package weavers.siltarae.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import weavers.siltarae.comment.domain.Comment;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentListResponse {
    private Long totalCount;
    private List<CommentResponse> comments;

    @Builder
    public CommentListResponse(Long totalCount, List<CommentResponse> comments) {
        this.totalCount = totalCount;
        this.comments = comments;
    }

    public static CommentListResponse from(Page<Comment> comments) {
        List<CommentResponse> commentResponses = comments.getContent().stream().map(
                CommentResponse::from
        ).toList();

        return CommentListResponse.builder()
                .totalCount(comments.getTotalElements())
                .comments(commentResponses)
                .build();
    }
}

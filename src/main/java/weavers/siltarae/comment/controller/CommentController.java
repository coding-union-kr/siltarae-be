package weavers.siltarae.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.comment.dto.request.CommentCreateRequest;
import weavers.siltarae.comment.dto.request.CommentListRequest;
import weavers.siltarae.comment.dto.response.CommentListResponse;
import weavers.siltarae.comment.service.CommentService;

@Tag(name = "[댓글] 댓글 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    private static final Long TEST_USER_ID = 1L;

    @GetMapping
    public ResponseEntity<CommentListResponse> getComments(
            @Valid CommentListRequest request) {

        return ResponseEntity.ok(commentService.getCommentList(request));
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody @Valid CommentCreateRequest request) {
        commentService.save(request, TEST_USER_ID);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Long commentId) {
        commentService.delete(commentId, TEST_USER_ID);

        return ResponseEntity.noContent().build();
    }
}

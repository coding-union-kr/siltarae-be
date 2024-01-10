package weavers.siltarae.comment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.comment.dto.request.CommentCreateRequest;
import weavers.siltarae.comment.dto.request.CommentListRequest;
import weavers.siltarae.comment.dto.response.CommentListResponse;
import weavers.siltarae.comment.dto.response.CommentResponse;
import weavers.siltarae.comment.service.CommentService;
import weavers.siltarae.login.Auth;

@Tag(name = "[댓글] 댓글 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentListResponse> getComments(
            @Valid CommentListRequest request) {

        return ResponseEntity.ok(commentService.getCommentList(request));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @Auth Long memberId,
            @RequestBody @Valid CommentCreateRequest request) {
        CommentResponse commentResponse = commentService.save(request, memberId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Auth Long memberId,
            @PathVariable("commentId") Long commentId) {
        commentService.delete(commentId, memberId);

        return ResponseEntity.noContent().build();
    }
}

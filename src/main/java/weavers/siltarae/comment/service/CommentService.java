package weavers.siltarae.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.comment.domain.Comment;
import weavers.siltarae.comment.domain.repository.CommentRepository;
import weavers.siltarae.comment.dto.request.CommentCreateRequest;
import weavers.siltarae.comment.dto.request.CommentListRequest;
import weavers.siltarae.comment.dto.response.CommentListResponse;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MistakeRepository mistakeRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public CommentListResponse getCommentList(CommentListRequest request) {
        Page<Comment> comments
                = commentRepository.findByMistakeAndDeletedAtIsNullOrderByIdDesc(getMistake(request.getMistakeId()), request.toPageable());

        return CommentListResponse.from(comments);
    }

    public void save(CommentCreateRequest request, Long memberId) {
        commentRepository.save(
                Comment.builder()
                        .member(getTestUser(memberId))
                        .mistake(getMistake(request.getMistakeId()))
                        .content(request.getContent())
                        .build()
        );
    }

    @Transactional
    public void delete(Long commentId, Long memberId) {
        Comment comment = findMyComment(commentId, getTestUser(memberId));

        comment.delete();
    }

    private Comment findMyComment(Long commentId, Member member) {
        return commentRepository.findByIdAndMemberAndDeletedAtIsNull(commentId, member).orElseThrow(() -> new BadRequestException(ExceptionCode.COMMENT_VALID_ERROR));
    }

    private Mistake getMistake(Long mistakeId) {
        return mistakeRepository.findByIdAndDeletedAtIsNull(mistakeId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MISTAKE));
    }

    private Member getTestUser(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_USER));
    }
}

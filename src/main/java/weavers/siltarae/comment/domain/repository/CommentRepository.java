package weavers.siltarae.comment.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.comment.domain.Comment;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.mistake.domain.Mistake;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByMistakeAndDeletedAtIsNullOrderByIdDesc(Mistake mistake, Pageable pageable);
    Optional<Comment> findByIdAndMember_IdAndDeletedAtIsNull(Long id, Long memberId);
}

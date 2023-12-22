package weavers.siltarae.mistake.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.mistake.domain.Mistake;

import java.util.List;
import java.util.Optional;

public interface MistakeRepository extends JpaRepository<Mistake, Long>, MistakeQRepository {
    Page<Mistake> findByMember_IdAndDeletedAtIsNullOrderByIdDesc(Long memberId, Pageable pageable);
    Optional<Mistake> findByIdAndMemberAndDeletedAtIsNull(Long id, Member member);
    List<Mistake> findByIdInAndMemberAndDeletedAtIsNull(List<Long> id, Member member);
    Optional<Mistake> findByIdAndDeletedAtIsNull(Long id);
    Page<Mistake> findByDeletedAtIsNullOrderByIdDesc(Pageable pageable);
    Page<Mistake> findByMember_IdAndTags_IdInAndDeletedAtIsNullOrderByIdDesc(Long memberId, List<Long> tagIds, Pageable pageable);
}

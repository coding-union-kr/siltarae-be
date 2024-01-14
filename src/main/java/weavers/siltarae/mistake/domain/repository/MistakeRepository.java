package weavers.siltarae.mistake.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.mistake.domain.Mistake;

import java.util.List;
import java.util.Optional;

public interface MistakeRepository extends JpaRepository<Mistake, Long>, MistakeQRepository {
    Page<Mistake> findByMember_IdAndDeletedAtIsNullOrderByIdDesc(Long memberId, Pageable pageable);
    List<Mistake> findByIdInAndMember_IdAndDeletedAtIsNull(List<Long> id, Long memberId);
    Optional<Mistake> findByIdAndDeletedAtIsNull(Long id);
    Page<Mistake> findByDeletedAtIsNullOrderByIdDesc(Pageable pageable);
    Page<Mistake> findByMember_IdAndTags_IdInAndDeletedAtIsNullOrderByIdDesc(Long memberId, List<Long> tagIds, Pageable pageable);
}

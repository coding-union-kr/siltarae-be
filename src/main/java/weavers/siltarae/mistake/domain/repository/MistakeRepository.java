package weavers.siltarae.mistake.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MistakeRepository extends JpaRepository<Mistake, Long> {
    Page<Mistake> findByMemberAndDeletedAtIsNullOrderByIdDesc(Member member, Pageable pageable);
    Optional<Mistake> findByIdAndMemberAndDeletedAtIsNull(Long id, Member member);
    List<Mistake> findByIdInAndMemberAndDeletedAtIsNull(List<Long> id, Member member);
}

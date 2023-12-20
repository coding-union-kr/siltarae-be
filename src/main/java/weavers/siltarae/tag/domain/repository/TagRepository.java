package weavers.siltarae.tag.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.tag.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByMember_IdAndName(Long memberId, String name);

    List<Tag> findAllByMember_IdAndDeletedAtIsNull(Long userId);
}


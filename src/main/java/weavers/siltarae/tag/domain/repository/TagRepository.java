package weavers.siltarae.tag.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.tag.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByUser_IdAndName(Long userId, String name);

    List<Tag> findAllByUser_IdAndDeletedAtIsNotNull(Long userId);
}

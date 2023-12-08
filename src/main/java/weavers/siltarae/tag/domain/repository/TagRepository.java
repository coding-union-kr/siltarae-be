package weavers.siltarae.tag.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

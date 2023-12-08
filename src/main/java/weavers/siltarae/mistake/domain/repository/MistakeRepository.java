package weavers.siltarae.mistake.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.mistake.domain.Mistake;

public interface MistakeRepository extends JpaRepository<Mistake, Long> {
}

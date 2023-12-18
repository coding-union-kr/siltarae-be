package weavers.siltarae.mistake.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import weavers.siltarae.mistake.domain.Mistake;

public interface MistakeQRepository {
    Page<Mistake> findMistakesSortedByLikes(Pageable pageable);
}

package weavers.siltarae.mistake.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.user.domain.User;

import java.util.Optional;

public interface MistakeRepository extends JpaRepository<Mistake, Long> {
    Page<Mistake> findByUserAndDeletedAtIsNullOrderByIdDesc(User user, Pageable pageable);
    Optional<Mistake> findByIdAndUserAndDeletedAtIsNull(Long id, User user);
}

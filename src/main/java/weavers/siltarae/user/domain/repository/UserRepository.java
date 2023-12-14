package weavers.siltarae.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weavers.siltarae.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

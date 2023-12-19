package weavers.siltarae.login.domain.repository;

import org.springframework.data.repository.CrudRepository;
import weavers.siltarae.login.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}

package weavers.siltarae.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weavers.siltarae.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}

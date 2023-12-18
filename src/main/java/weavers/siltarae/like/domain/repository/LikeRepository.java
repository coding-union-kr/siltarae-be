package weavers.siltarae.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weavers.siltarae.like.domain.LikeId;
import weavers.siltarae.like.domain.Likes;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.mistake.domain.Mistake;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, LikeId> {
    Optional<Likes> findByMemberAndMistake(Member member, Mistake mistake);
}

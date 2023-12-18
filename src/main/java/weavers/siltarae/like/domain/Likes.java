package weavers.siltarae.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.CreateTimeEntity;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.mistake.domain.Mistake;

@Entity
@IdClass(LikeId.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends CreateTimeEntity {
    @Id
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Id
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "mistake_id", nullable = false)
    private Mistake mistake;

    @Builder
    public Likes(Member member, Mistake mistake) {
        this.member = member;
        this.mistake = mistake;
    }
}

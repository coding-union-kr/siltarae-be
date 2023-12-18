package weavers.siltarae.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.BaseEntity;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.mistake.domain.Mistake;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 140)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mistake_id")
    private Mistake mistake;

    @Builder
    public Comment(Long id, Member member, String content, Mistake mistake) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.mistake = mistake;
    }
}

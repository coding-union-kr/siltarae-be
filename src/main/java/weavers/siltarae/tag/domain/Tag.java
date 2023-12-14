package weavers.siltarae.tag.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.BaseEntity;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.member.domain.Member;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tag_mistake",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "mistake_id"))
    private List<Mistake> mistakes;

    @Builder
    public Tag(Long id, String name, Member member, List<Mistake> mistakes) {
        this.id = id;
        this.name = name;
        this.member = member;
        this.mistakes = mistakes;
    }

    @Override
    public void delete() {
        super.delete();
        this.getMistakes().clear();
    }
}

package weavers.siltarae.mistake.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.BaseEntity;
import weavers.siltarae.like.domain.Likes;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.tag.domain.Tag;

import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
public class Mistake extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 140)
    private String content;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tag_mistake",
            joinColumns = @JoinColumn(name = "mistake_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Builder.Default
    private List<Tag> tags = Collections.emptyList();;

    @OneToMany(mappedBy = "mistake", cascade = CascadeType.DETACH)
    @Builder.Default
    private List<Likes> likes = Collections.emptyList();

    public Mistake(Long id, Member member, String content, List<Tag> tags, List<Likes> likes) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.tags = tags;
        this.likes = likes;
    }

}

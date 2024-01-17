package weavers.siltarae.mistake.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.comment.domain.Comment;
import weavers.siltarae.global.BaseEntity;
import weavers.siltarae.like.domain.Likes;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.tag.domain.Tag;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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
    private List<Tag> tags;

    @OneToMany(mappedBy = "mistake", cascade = CascadeType.DETACH)
    private List<Comment> comments;

    @OneToMany(mappedBy = "mistake", cascade = CascadeType.DETACH)
    private List<Likes> likes;

    @Builder
    public Mistake(Long id, Member member, String content, List<Tag> tags, List<Likes> likes) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.tags = tags;
        this.likes = likes;
    }

    public Integer getExistingCommentCount(Mistake mistake) {
        return mistake.getComments().stream()
                .filter(comment -> comment.getDeletedAt() == null)
                .mapToInt(comment -> 1)
                .sum();
    }

}

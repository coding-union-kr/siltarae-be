package weavers.siltarae.mistake.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Mistake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 140)
    private String content;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tag_mistake",
            joinColumns = @JoinColumn(name = "mistake_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @Builder
    public Mistake(Long id, User user, String content, LocalDateTime createdAt, List<Tag> tags) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.tags = tags;
    }

    public void deleteMistake(Mistake mistake) {
        mistake.deletedAt = LocalDateTime.now();
    }
}

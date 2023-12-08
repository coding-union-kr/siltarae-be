package weavers.siltarae.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Builder
    public User(Long id, String identifier, String nickname, String email, LocalDateTime createdAt, SocialType socialType) {
        this.id = id;
        this.identifier = identifier;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.socialType = socialType;
    }
}
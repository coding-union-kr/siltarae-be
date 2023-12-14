package weavers.siltarae.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.BaseEntity;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Builder
    public Member(Long id, String identifier, String nickname, String email, SocialType socialType) {
        this.id = id;
        this.identifier = identifier;
        this.nickname = nickname;
        this.email = email;
        this.socialType = socialType;
    }
}
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

    private static final String DELETED_MEMBER_NICKNAME = "탈퇴회원";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String identifier;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column
    private SocialType socialType;

    public void update(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void delete() {
        super.delete();

        this.identifier = null;
        this.nickname = DELETED_MEMBER_NICKNAME;
        this.email = null;
    }

    @Builder
    public Member(Long id, String identifier, String nickname, String email, SocialType socialType) {
        this.id = id;
        this.identifier = identifier;
        this.nickname = nickname;
        this.email = email;
        this.socialType = socialType;
    }
}
package weavers.siltarae.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.BaseEntity;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.global.util.CryptoUtil;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private static final String DELETED_MEMBER_NICKNAME = "탈퇴회원";
    private static final String DEFAULT_IMAGE = "https://weavers-siltarae.s3.ap-northeast-2.amazonaws.com/profile/default_image.png";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String identifier;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column
    private String email;

    @Column
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    @Column
    private SocialType socialType;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void delete() {
        super.delete();

        this.identifier = null;
        this.nickname = DELETED_MEMBER_NICKNAME;
        this.email = null;
        this.imageUrl = this.getImagePath() + DEFAULT_IMAGE;
    }

    public boolean hasDefaultImage() {
        return this.getImageUrl().equals(DEFAULT_IMAGE);
    }

    public String getDefaultImage() {
        return DEFAULT_IMAGE;
    }

    private String getImagePath() {
        return this.imageUrl.substring(0, this.imageUrl.lastIndexOf("/")+1);
    }

    public String getImageName() {
        return this.imageUrl.substring(this.imageUrl.lastIndexOf("/")+1);
    }

    @PrePersist
    @PreUpdate
    public void encryptEmail() {
        try {
            this.email = CryptoUtil.encrypt(email);
        } catch (NullPointerException e) {
            throw new BadRequestException(ExceptionCode.INVALID_EMAIL);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionCode.FAIL_ENCRYPT);
        }
    }

    @PostLoad
    public void decryptEmail() {
        try {
            this.email = CryptoUtil.decrypt(email);
        } catch (Exception e) {
            throw new BadRequestException(ExceptionCode.FAIL_DECRYPT);
        }
    }

    @Builder
    public Member(Long id, String identifier, String nickname, String email, SocialType socialType) {
        this.id = id;
        this.identifier = identifier;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = DEFAULT_IMAGE;
        this.socialType = socialType;
    }
}
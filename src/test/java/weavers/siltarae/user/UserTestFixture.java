package weavers.siltarae.user;

import weavers.siltarae.user.domain.SocialType;
import weavers.siltarae.user.domain.User;

public class UserTestFixture {

    public static User USER_KIM() {
        return User.builder()
                .id(1L)
                .email("siltarae@nn.com")
                .nickname("Kim")
                .socialType(SocialType.GOOGLE)
                .build();
    }
}

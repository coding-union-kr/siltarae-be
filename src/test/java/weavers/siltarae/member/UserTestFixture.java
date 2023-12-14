package weavers.siltarae.member;

import weavers.siltarae.member.domain.SocialType;
import weavers.siltarae.member.domain.Member;

public class UserTestFixture {

    public static Member USER_KIM() {
        return Member.builder()
                .id(1L)
                .email("siltarae@nn.com")
                .nickname("Kim")
                .socialType(SocialType.GOOGLE)
                .build();
    }
}

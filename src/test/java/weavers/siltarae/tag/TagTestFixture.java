package weavers.siltarae.tag;

import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.SocialType;
import weavers.siltarae.tag.domain.Tag;

public class TagTestFixture {

    public static Tag COMPANY_TAG() {
        return Tag.builder()
                .id(1L)
                .member(new Member(1L, "12345", "실타래", "siltarae@nn.com", SocialType.GOOGLE))
                .name("회사")
                .build();
    }

    public static Tag DAILY_TAG() {
        return Tag.builder()
                .id(2L)
                .member(new Member(1L, "12345", "실타래", "siltarae@nn.com", SocialType.GOOGLE))
                .name("일상")
                .build();
    }

    public static Tag DELETED_TAG() {
        Tag deletedTag = Tag.builder()
                            .id(3L)
                            .member(new Member(1L, "12345", "실타래", "siltarae@nn.com", SocialType.GOOGLE))
                            .name("모의고사")
                            .build();

        deletedTag.delete();

        return deletedTag;
    }

    public static Tag OTHERS_TAG() {
        return Tag.builder()
                .id(4L)
                .member(new Member(2L, "54321", "꿀타래", "kkultarae@nn.com", SocialType.GOOGLE))
                .name("학생회")
                .build();
    }
}

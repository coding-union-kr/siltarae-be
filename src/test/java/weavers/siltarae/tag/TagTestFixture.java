package weavers.siltarae.tag;

import weavers.siltarae.tag.domain.Tag;

public class TagTestFixture {

    public static Tag COMPANY_TAG() {
        return Tag.builder()
                .id(1L)
                .name("회사")
                .build();
    }

    public static Tag DAILY_TAG() {
        return Tag.builder()
                .id(1L)
                .name("일상")
                .build();
    }

    public static Tag DELETED_TAG() {
        Tag deletedTag = Tag.builder()
                            .id(1L)
                            .name("모의고사")
                            .build();

        deletedTag.delete();

        return deletedTag;
    }
}

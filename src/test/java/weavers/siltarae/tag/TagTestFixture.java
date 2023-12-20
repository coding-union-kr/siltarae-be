package weavers.siltarae.tag;

import weavers.siltarae.tag.domain.Tag;

import java.util.List;

public class TagTestFixture {

    public static Tag COMPANY_TAG() {
        return Tag.builder()
                .id(1L)
                .name("회사")
                .mistakes(List.of())
                .build();
    }

    public static Tag DAILY_TAG() {
        return Tag.builder()
                .id(1L)
                .name("일상")
                .mistakes(List.of())
                .build();
    }

    public static Tag DELETED_TAG() {
        Tag deletedTag = Tag.builder()
                            .id(1L)
                            .name("모의고사")
                            .mistakes(List.of())
                            .build();

        deletedTag.delete();

        return deletedTag;
    }
}

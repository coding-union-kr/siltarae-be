package weavers.siltarae.tag;

import weavers.siltarae.tag.domain.Tag;

import java.time.LocalDateTime;

public class TagTestFixture {

    public static Tag COMPANY_TAG() {
        return Tag.builder()
                .id(1L)
                .name("회사")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Tag DAILY_TAG() {
        return Tag.builder()
                .id(1L)
                .name("일상")
                .createdAt(LocalDateTime.now())
                .build();
    }
}

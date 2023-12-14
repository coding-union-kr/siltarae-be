package weavers.siltarae.tag.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.user.UserTestFixture;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TagTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    void 태그_생성_시_생성일이_자동으로_지정된다() {
        // given
        Tag createdTag = Tag.builder()
                .name("tag")
                .user(UserTestFixture.USER_KIM())
                .build();

        // when
        Tag savedTag = tagRepository.save(createdTag);
        System.out.println("createdAt: " + savedTag.getCreatedAt());

        // then
        assertThat(savedTag.getCreatedAt()).isBefore(LocalDateTime.now());
    }
}
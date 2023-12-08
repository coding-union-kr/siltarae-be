package weavers.siltarae.tag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.user.domain.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static weavers.siltarae.tag.TagTestFixture.COMPANY_TAG;
import static weavers.siltarae.user.UserTestFixture.USER_KIM;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 태그_생성_후_tagId를_반환한다() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest("tagName");
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(USER_KIM()));
        given(tagRepository.save(any(Tag.class)))
                .willReturn(COMPANY_TAG());

        // when
        final Long actualId = tagService.save(1L, tagCreateRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }

    @Test
    void 태그명_중복_시_예외가_발생한다() {
        // given
        TagCreateRequest request = new TagCreateRequest("회사");
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(USER_KIM()));
        given(tagRepository.existsByUser_UserIdAndName(1L, "회사"))
                .willReturn(true);

        // when
        BadRequestException e = assertThrows(BadRequestException.class,
                () -> tagService.save(1L, request));

        // then
        assertThat(e.getMessage()).isEqualTo("중복된 태그명입니다.");
    }
}
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
import weavers.siltarae.tag.dto.response.TagListResponse;
import weavers.siltarae.member.domain.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static weavers.siltarae.tag.TagTestFixture.*;
import static weavers.siltarae.member.UserTestFixture.USER_KIM;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 태그_생성_후_tagId를_반환한다() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(COMPANY_TAG().getName());
        given(memberRepository.findById(2L))
                .willReturn(Optional.ofNullable(USER_KIM()));
        given(tagRepository.save(any(Tag.class)))
                .willReturn(COMPANY_TAG());

        // when
        final Long actualId = tagService.save(2L, tagCreateRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }

    @Test
    void 태그명_중복_시_예외가_발생한다() {
        // given
        TagCreateRequest request = new TagCreateRequest("회사");
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(USER_KIM()));
        given(tagRepository.existsByMember_IdAndName(1L, "회사"))
                .willReturn(true);

        // when
        BadRequestException e = assertThrows(BadRequestException.class,
                () -> tagService.save(1L, request));

        // then
        assertThat(e.getMessage()).isEqualTo("중복된 태그명입니다.");
    }

    @Test
    void 사용자의_태그_목록을_조회할_수_있다() {
        // given
        given(tagRepository.findAllByMember_IdAndDeletedAtIsNull(1L))
                .willReturn(List.of(COMPANY_TAG(), DAILY_TAG()));

        // when
        TagListResponse tagList = tagService.getTagList(1L);

        // then
        assertThat(tagList.getTotalCount()).isEqualTo(2);
    }

    @Test
    void 이미_삭제된_태그를_삭제하면_예외가_발생한다() {
        // given
        given(tagRepository.findAllById(anyList()))
                .willReturn(List.of(COMPANY_TAG(), DELETED_TAG(), DAILY_TAG()));
        List<Long> tagIdList = List.of(1L, 2L, 3L);

        // when

        // then
        assertThatThrownBy(() -> tagService.deleteTags(1L, tagIdList))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("해당하는 태그가 존재하지 않습니다.");
    }

    @Test
    void 타인의_태그를_삭제하면_예외가_발생한다() {
        // given
        given(tagRepository.findAllById(anyList()))
                .willReturn(List.of(COMPANY_TAG(), OTHERS_TAG(), DAILY_TAG()));
        List<Long> tagIdList = List.of(1L, 2L, 4L);

        // when

        // then
        assertThatThrownBy(() -> tagService.deleteTags(1L, tagIdList))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("해당하는 태그가 존재하지 않습니다.");
    }

}
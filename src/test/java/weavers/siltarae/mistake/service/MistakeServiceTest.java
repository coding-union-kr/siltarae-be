package weavers.siltarae.mistake.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.member.domain.SocialType;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Transactional
public class MistakeServiceTest {
    @InjectMocks
    private MistakeService mistakeService;
    @Mock
    private MistakeRepository mistakeRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private MemberRepository memberRepository;

    Member MOCK_Member = Member.builder()
            .id(1L)
            .email("siltarae@nn.com")
            .nickname("실타래")
            .socialType(SocialType.GOOGLE)
            .build();

    Tag MOCK_TAG = Tag.builder()
            .id(1L)
            .name("태그")
            .member(MOCK_Member)
            .build();

    Mistake MOCK_MISTAKE = Mistake.builder()
            .id(1L)
            .tags(Collections.singletonList(MOCK_TAG))
            .member(MOCK_Member)
            .content("실수 내용 입니다")
            .build();

    @Test
    public void 실수를_등록한다() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.ofNullable(MOCK_Member));
        given(tagRepository.findAllById(anyList())).willReturn(Collections.singletonList(MOCK_TAG));
        given(mistakeRepository.save(any(Mistake.class))).willReturn(MOCK_MISTAKE);

        // when
        Long mistakeId = mistakeService.save(
                MistakeCreateRequest.builder()
                        .tagIds(Collections.singletonList(1L))
                        .content("실수 내용 입니다")
                        .build(), 1L
        );

        // then
        assertThat(mistakeId).isEqualTo(MOCK_MISTAKE.getId());
    }

}

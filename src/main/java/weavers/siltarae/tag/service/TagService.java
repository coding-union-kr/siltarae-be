package weavers.siltarae.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.tag.dto.response.TagListResponse;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;
import weavers.siltarae.tag.dto.response.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    private final MistakeRepository mistakeRepository;

    private final MemberRepository memberRepository;

    public TagResponse save(final Long memberId, final TagCreateRequest tagCreateRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));

        if (checkDuplicateTagName(member.getId(), tagCreateRequest.getName())) {
            throw new BadRequestException(DUPLICATED_TAG_NAME);
        }

        final Tag createdTag = Tag.builder()
                .name(tagCreateRequest.getName())
                .member(member)
                .build();

        Tag tag = tagRepository.save(createdTag);

        return TagResponse.from(tag, mistakeRepository.countByTags_IdAndDeletedAtIsNull(tag.getId()));
    }

    @Transactional(readOnly = true)
    public TagListResponse getTagList(final Long memberId) {
        List<Tag> tagList = tagRepository.findAllByMember_IdAndDeletedAtIsNull(memberId);
        List<TagResponse> tagResponseList = tagList.stream()
                .map(tag -> TagResponse.from(
                        tag,
                        mistakeRepository.countByTags_IdAndDeletedAtIsNull(tag.getId())
                ))
                .collect(Collectors.toList());

        return TagListResponse.from(tagResponseList);
    }

    public void deleteTags(final Long memberId, final List<Long> tagIdList) {
        List<Tag> tagList = tagRepository.findAllById(tagIdList);

        if(hasDeletedTag(tagList) || hasOthersTag(memberId, tagList)) {
            throw new BadRequestException(NOT_FOUND_TAG);
        }

        tagList.forEach(Tag::delete);
    }

    private boolean checkDuplicateTagName(final Long memberId, final String tagName) {
        return tagRepository.existsByMember_IdAndNameAndDeletedAtIsNull(memberId, tagName);
    }

    private boolean hasDeletedTag(List<Tag> tagList) {
        return tagList.stream().anyMatch(Tag::isDeleted);
    }

    private boolean hasOthersTag(Long memberId, List<Tag> tagList) {
        return tagList.stream()
                .anyMatch(tag -> !memberId.equals(tag.getMember().getId()));
    }


}

package weavers.siltarae.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.tag.dto.response.TagListResponse;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;

import java.util.List;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    private final MemberRepository memberRepository;

    public Long save(final Long memberId, final TagCreateRequest tagCreateRequest) {
        log.info("============= memberId : {} =============", memberId);
        List<Member> allMember = memberRepository.findAll();
        allMember.forEach(
                member -> log.info("============= memberId : {} =============", member.getId())
        );

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

        return tag.getId();
    }

    @Transactional(readOnly = true)
    public TagListResponse getTagList(final Long memberId) {
        List<Tag> tagList = tagRepository.findAllByMember_IdAndDeletedAtIsNull(memberId);

        return TagListResponse.from(tagList);
    }

    public void deleteTags(final List<Long> tagIdList) {
        List<Tag> tagList = tagRepository.findAllById(tagIdList);

        if(hasDeletedTag(tagList)) {
            throw new BadRequestException(NOT_FOUND_TAG);
        }

        tagList.forEach(Tag::delete);
    }

    private boolean checkDuplicateTagName(final Long memberId, final String tagName) {
        return tagRepository.existsByMember_IdAndName(memberId, tagName);
    }

    private boolean hasDeletedTag(List<Tag> tagList) {
        return tagList.stream().anyMatch(Tag::isDeleted);
    }


}

package weavers.siltarae.mistake.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
import weavers.siltarae.mistake.dto.request.MistakeListRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.dto.response.MistakeResponse;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MistakeService {
    private final MemberRepository memberRepository;
    private final MistakeRepository mistakeRepository;
    private final TagRepository tagRepository;

    public Long save(
            MistakeCreateRequest request, Long memberId) {
        Mistake mistake = mistakeRepository.save(
                Mistake.builder()
                        .member(getTestUser(memberId))
                        .content(request.getContent())
                        .tags(getTags(request.getTagIds(), memberId))
                        .build()
        );

        return mistake.getId();
    }

    @Transactional(readOnly = true)
    public MistakeListResponse getMistakeList(Long memberId, MistakeListRequest request) {
        List<Long> tagIds = getTagIdsFromRequest(request);
        List<Tag> tags = getTags(tagIds, memberId);
        Page<Mistake> mistakes = getMistakes(memberId, tagIds, request.toPageable());

        return MistakeListResponse.from(mistakes, tags);
    }

    private List<Long> getTagIdsFromRequest(MistakeListRequest request) {
        return request.getTag() != null ? getLongs(request) : Collections.emptyList();
    }

    private Page<Mistake> getMistakes(Long memberId, List<Long> tagIds, Pageable pageable) {
        if (tagIds.isEmpty()) {
            return mistakeRepository.findByMember_IdAndDeletedAtIsNullOrderByIdDesc(memberId, pageable);
        }

        return mistakeRepository.findByMember_IdAndTags_IdInAndDeletedAtIsNullOrderByIdDesc(memberId, tagIds, pageable);
    }

    private static List<Long> getLongs(MistakeListRequest request) {
        return Arrays.stream(request.getTag().split(","))
                .map(Long::parseLong)
                .toList();
    }

    @Transactional(readOnly = true)
    public MistakeResponse getMistake(Long memberId, Long mistakeId) {
        Mistake mistake = mistakeRepository.findByIdAndMemberAndDeletedAtIsNull(mistakeId, getTestUser(memberId))
        .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MISTAKE)
        );

        return MistakeResponse.from(mistake);
    }

    @Transactional
    public void deleteMistake(Long memberId, List<Long> mistakeIds) {
        List<Mistake> mistakes
                = mistakeRepository.findByIdInAndMemberAndDeletedAtIsNull(mistakeIds, getTestUser(memberId));

        mistakes.forEach(Mistake::delete);
    }

    private Member getTestUser(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_MISTAKE_CONTENT_NULL));
    }

    private List<Tag> getTags(List<Long> tagIds, Long memberId) {
        List<Tag> tags
                = tagRepository.findAllById(tagIds);

        validateTag(tagIds, memberId, tags);

        return tags;
    }

    private static void validateTag(List<Long> tagIds, Long memberId, List<Tag> tags) {
        validateTagByUser(tagIds, memberId, tags);
        validateExistingTag(tagIds, tags);
    }

    private static void validateTagByUser(List<Long> tagIds, Long memberId, List<Tag> tags) {
        if (tags.stream().anyMatch(tag -> !Objects.equals(tag.getMember().getId(), memberId))) {
            throw new BadRequestException(ExceptionCode.INTERNAL_SEVER_ERROR);
        }
    }

    private static void validateExistingTag(List<Long> tagIds, List<Tag> tags) {
        if (!Objects.equals(tags.size(), tagIds.size())) {
            throw new BadRequestException(ExceptionCode.INTERNAL_SEVER_ERROR);
        }
    }

}

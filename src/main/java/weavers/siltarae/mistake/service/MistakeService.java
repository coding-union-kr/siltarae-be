package weavers.siltarae.mistake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.global.util.LikeAbleUtil;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
import weavers.siltarae.mistake.dto.request.MistakeListRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.dto.response.MistakeResponse;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.domain.repository.TagRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MistakeService {
    private final MemberRepository memberRepository;
    private final MistakeRepository mistakeRepository;
    private final TagRepository tagRepository;
    private final LikeAbleUtil likeAbleUtil;

    public Long save(
            MistakeCreateRequest request, Long memberId) {
        Mistake mistake = mistakeRepository.save(
                Mistake.builder()
                        .member(getMemberFromId(memberId))
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

        return MistakeListResponse.from(getMistakeResponseList(mistakes.getContent()), tags, mistakes.getTotalElements());
    }

    private List<MistakeResponse> getMistakeResponseList(List<Mistake> mistakes) {
        return mistakes.stream()
                .map(mistake -> MistakeResponse.from(
                        mistake, likeAbleUtil.getLikeAble(mistake.getId(), mistake.getMember().getId())
                ))
                .collect(Collectors.toList());
    }

    private List<Long> getTagIdsFromRequest(MistakeListRequest request) {
        if (!request.getTag().isEmpty()) {
            return Arrays.stream(request.getTag().split(","))
                    .map(Long::parseLong)
                    .toList();
        }

        return Collections.emptyList();
    }

    private Page<Mistake> getMistakes(Long memberId, List<Long> tagIds, Pageable pageable) {
        if (tagIds.isEmpty()) {
            return mistakeRepository.findByMember_IdAndDeletedAtIsNullOrderByIdDesc(memberId, pageable);
        }

        return mistakeRepository.findByMember_IdAndTags_IdInAndDeletedAtIsNullOrderByIdDesc(memberId, tagIds, pageable);
    }

    @Transactional(readOnly = true)
    public MistakeResponse getMistake(Long mistakeId) {
        Mistake mistake = mistakeRepository.findByIdAndDeletedAtIsNull(mistakeId)
        .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MISTAKE)
        );

        return MistakeResponse.from(mistake, likeAbleUtil.getLikeAble(mistakeId, mistake.getMember().getId()));
    }

    @Transactional
    public void deleteMistake(Long memberId, List<Long> mistakeIds) {
        List<Mistake> mistakes
                = mistakeRepository.findByIdInAndMember_IdAndDeletedAtIsNull(mistakeIds, memberId);

        mistakes.forEach(Mistake::delete);
    }

    private Member getMemberFromId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_MISTAKE_CONTENT_NULL));
    }

    private List<Tag> getTags(List<Long> tagIds, Long memberId) {
        List<Tag> tags = tagRepository.findByIdInAndDeletedAtIsNull(tagIds);

        validateTagByUser(memberId, tags);
        validateExistingTag(tagIds, tags);

        return tags;
    }

    private static void validateTagByUser(Long memberId, List<Tag> tags) {
        if (tags.stream().anyMatch(tag -> !Objects.equals(tag.getMember().getId(), memberId))) {
            throw new BadRequestException(ExceptionCode.NOT_FOUND_TAG_WITH_MEMBER);
        }
    }

    private static void validateExistingTag(List<Long> tagIds, List<Tag> tags) {
        if (!Objects.equals(tags.size(), tagIds.size())) {
            throw new BadRequestException(ExceptionCode.NOT_FOUND_TAG);
        }
    }

}

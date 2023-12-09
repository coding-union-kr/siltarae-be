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
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.dto.response.MistakeResponse;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.tag.dto.response.TagResponse;
import weavers.siltarae.user.domain.User;
import weavers.siltarae.user.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MistakeService {
    private final UserRepository userRepository;
    private final MistakeRepository mistakeRepository;
    private final TagRepository tagRepository;

    public Long save(
            MistakeCreateRequest request, Long memberId) {
        Mistake mistake = mistakeRepository.save(
                Mistake.builder()
                        .user(getTestUser(memberId))
                        .content(request.getContent())
                        .createdAt(LocalDateTime.now())
                        .tags(getTags(request.getTagIds(), memberId))
                        .build()
        );

        return mistake.getId();
    }

    @Transactional(readOnly = true)
    public MistakeListResponse getMistakeList(Long memberId, Pageable pageable) {
        Page<Mistake> mistakes = mistakeRepository.findByUserAndDeletedAtIsNullOrderByIdDesc(getTestUser(memberId), pageable);

        return MistakeListResponse.from(mistakes);
    }

    @Transactional(readOnly = true)
    public MistakeResponse getMistake(Long memberId, Long mistakeId) {
        Mistake mistake = mistakeRepository.findByIdAndUserAndDeletedAtIsNull(mistakeId, getTestUser(memberId))
        .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MISTAKE)
        );

        return MistakeResponse.from(mistake);
    }

    @Transactional
    public void deleteMistake(Long memberId, List<Long> mistakeIds) {
        List<Mistake> mistakes
                = mistakeRepository.findByIdInAndUserAndDeletedAtIsNull(mistakeIds, getTestUser(memberId));

        mistakes.forEach(
                mistake -> mistake.deleteMistake(mistake)
        );
    }

    private User getTestUser(Long memberId) {
        return userRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_MISTAKE_CONTENT_NULL));
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
        if (tags.stream().anyMatch(tag -> !Objects.equals(tag.getUser().getId(), memberId))) {
            throw new BadRequestException(ExceptionCode.INTERNAL_SEVER_ERROR);
        }
    }

    private static void validateExistingTag(List<Long> tagIds, List<Tag> tags) {
        if (!Objects.equals(tags.size(), tagIds.size())) {
            throw new BadRequestException(ExceptionCode.INTERNAL_SEVER_ERROR);
        }
    }

}

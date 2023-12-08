package weavers.siltarae.mistake.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.dto.response.MistakeResponse;
import weavers.siltarae.mistake.dto.response.TagResponse;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.domain.repository.TagRepository;
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
        validateOfMistakeContent(request.getContent());

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

    private static final Integer TEST_COMMENT_COUNT = 13;
    private static final Integer TEST_LIKE_COUNT = 11;

    public MistakeListResponse getMistakeList(Long memberId, Pageable pageable) {
        Page<Mistake> mistakes = mistakeRepository.findByUserAndDeletedAtIsNullOrderByIdDesc(getTestUser(memberId), pageable);

        return MistakeListResponse.builder()
                .totalCount(mistakes.getTotalElements())
                .mistakes(
                        mistakes.getContent().stream().map(
                                mistake -> MistakeResponse.builder()
                                        .id(mistake.getId())
                                        .content(mistake.getContent())
                                        .commentCount(TEST_COMMENT_COUNT)
                                        .likeCount(TEST_LIKE_COUNT)
                                        .tags(
                                                mistake.getTags().stream().map(
                                                        tag -> TagResponse.builder()
                                                                .id(tag.getId())
                                                                .name(tag.getName())
                                                                .build()
                                                ).collect(Collectors.toList())
                                        )
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }

    private static final int MAX_MISTAKE_CONTENT_SIZE = 140;
    private static final int MIN_MISTAKE_CONTENT_SIZE = 0;
    private void validateOfMistakeContent(String content) {
        validateMaxSizeMistakeContent(content);
        validateMinSizeMistakeContent(content);

        validateMistakeContent(content);
    }

    private void validateMistakeContent(String content) {
        boolean test = Pattern.matches("^[0-9a-zA-Zㄱ-ㅎ가-힣 .,]*$", content);

        if (!test) {
            throw new BadRequestException(ExceptionCode.INVALID_MISTAKE_CONTENT);
        }
    }

    private void validateMinSizeMistakeContent(String content) {
        if (content.length() == MIN_MISTAKE_CONTENT_SIZE) {
            throw new BadRequestException(ExceptionCode.INVALID_MISTAKE_CONTENT_NULL);
        }
    }

    private void validateMaxSizeMistakeContent(String content) {
        if (content.length() > MAX_MISTAKE_CONTENT_SIZE) {
            throw new BadRequestException(ExceptionCode.INVALID_MISTAKE_CONTENT_SIZE);
        }
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

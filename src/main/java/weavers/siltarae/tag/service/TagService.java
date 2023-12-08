package weavers.siltarae.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.tag.domain.repository.TagRepository;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.user.domain.User;
import weavers.siltarae.user.domain.repository.UserRepository;

import java.time.LocalDateTime;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    private final UserRepository userRepository;

    public Long save(final Long userId, final TagCreateRequest tagCreateRequest) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(INVALID_REQUEST));

        if (checkDuplicateTagName(user.getId(), tagCreateRequest.getName())) {
            throw new BadRequestException(DUPLICATED_TAG_NAME);
        }

        final Tag createdTag = Tag.builder()
                .name(tagCreateRequest.getName())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        Tag tag = tagRepository.save(createdTag);

        return tag.getId();
    }

    private boolean checkDuplicateTagName(final Long userId, final String tagName) {
        return tagRepository.existsByUser_UserIdAndName(userId, tagName);
    }
}

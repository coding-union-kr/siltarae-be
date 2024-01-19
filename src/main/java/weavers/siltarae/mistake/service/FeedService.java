package weavers.siltarae.mistake.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.like.domain.repository.LikeRepository;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.FeedRequest;
import weavers.siltarae.mistake.dto.response.FeedListResponse;
import weavers.siltarae.mistake.dto.response.FeedResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FeedService {
    private final MistakeRepository mistakeRepository;
    private final LikeRepository likeRepository;

    public FeedListResponse getFeedList(FeedRequest request, Long memberId) {
        Page<Mistake> mistakes = switch (request.getFeedType()) {
            case FASTEST -> mistakeRepository.findByDeletedAtIsNullOrderByIdDesc(request.toPageable());
            case POPULAR -> mistakeRepository.findMistakesSortedByLikes(request.toPageable());
        };

        List<FeedResponse> feedResponses = getFeedResponseList(mistakes.getContent(), memberId);
        return FeedListResponse.from(feedResponses, mistakes.getTotalElements());
    }

    private List<FeedResponse> getFeedResponseList(List<Mistake> mistakes, Long memberId) {
        return mistakes.stream()
                .map(mistake -> FeedResponse.from(
                        mistake, likeRepository.existsByMistake_IdAndMember_Id(mistake.getId(), memberId)
                ))
                .collect(Collectors.toList());
    }

}

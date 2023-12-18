package weavers.siltarae.mistake.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.FeedRequest;
import weavers.siltarae.mistake.dto.response.FeedListResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {
    private final MistakeRepository mistakeRepository;

    public FeedListResponse getfeedList(FeedRequest request) {
        Page<Mistake> mistakes = switch (request.getFeedType()) {
            case FASTEST -> mistakeRepository.findByDeletedAtIsNullOrderByIdDesc(request.toPageable());
            case POPULAR -> mistakeRepository.findMistakesSortedByLikes(request.toPageable());
        };

        return FeedListResponse.from(mistakes);
    }


}

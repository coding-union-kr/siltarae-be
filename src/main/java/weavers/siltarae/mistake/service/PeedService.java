package weavers.siltarae.mistake.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;
import weavers.siltarae.mistake.dto.request.PeedRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeedService {
    private final MistakeRepository mistakeRepository;

    public MistakeListResponse getPeedList(PeedRequest request) {
        Page<Mistake> mistakes = switch (request.getPeedType()) {
            case FASTEST -> mistakeRepository.findByDeletedAtIsNullOrderByIdDesc(request.toPageable());
            case POPULAR -> mistakeRepository.findMistakesSortedByLikes(request.toPageable());
        };

        return MistakeListResponse.peedFrom(mistakes.getContent(), mistakes.getTotalElements());
    }


}

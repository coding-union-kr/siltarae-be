package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import weavers.siltarae.mistake.domain.Mistake;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeListResponse {
    private Long totalCount;
    private List<MistakeResponse> mistakes;

    @Builder
    public MistakeListResponse(Long totalCount, List<MistakeResponse> mistakes) {
        this.totalCount = totalCount;
        this.mistakes = mistakes;
    }

    public static MistakeListResponse from (Page<Mistake> mistakes) {
        List<MistakeResponse> mistakeResponses = mistakes.getContent().stream().map(
                MistakeResponse::from
        ).collect(Collectors.toList());

        return MistakeListResponse.builder()
                .totalCount(mistakes.getTotalElements())
                .mistakes(mistakeResponses)
                .build();
    }
}

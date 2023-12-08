package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
}

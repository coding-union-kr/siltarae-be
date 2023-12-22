package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.tag.domain.Tag;
import weavers.siltarae.tag.dto.response.MistakeTagResponse;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeListResponse {
    private Long totalCount;
    private List<MistakeResponse> mistakes;
    private List<MistakeTagResponse> tags;

    @Builder
    public MistakeListResponse(Long totalCount, List<MistakeResponse> mistakes, List<MistakeTagResponse> tags) {
        this.totalCount = totalCount;
        this.mistakes = mistakes;
        this.tags = tags;
    }

    public static MistakeListResponse from (Page<Mistake> mistakes, List<Tag> tag) {
        List<MistakeResponse> mistakeResponses = mistakes.getContent().stream().map(
                MistakeResponse::from
        ).collect(Collectors.toList());

        List<MistakeTagResponse> tagResponses = tag.stream()
        .map(MistakeTagResponse::from)
        .toList();

        return MistakeListResponse.builder()
                .totalCount(mistakes.getTotalElements())
                .mistakes(mistakeResponses)
                .tags(tagResponses)
                .build();
    }
}

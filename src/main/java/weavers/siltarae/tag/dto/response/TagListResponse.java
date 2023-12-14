package weavers.siltarae.tag.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.tag.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagListResponse {

    private Integer totalCount;

    private List<TagResponse> tags;

    @Builder
    public TagListResponse(Integer totalCount, List<TagResponse> tags) {
        this.totalCount = totalCount;
        this.tags = tags;
    }

    public static TagListResponse from(List<Tag> tagList) {
        List<TagResponse> tagResponseList = tagList.stream()
                .map(TagResponse::from).collect(Collectors.toList());

        return TagListResponse.builder()
                .totalCount(tagResponseList.size())
                .tags(tagResponseList)
                .build();
    }
}

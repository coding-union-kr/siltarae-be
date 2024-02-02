package weavers.siltarae.tag.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public static TagListResponse from(List<TagResponse> tagResponseList) {
        return TagListResponse.builder()
                .totalCount(tagResponseList.size())
                .tags(tagResponseList)
                .build();
    }
}

package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.tag.dto.response.TagResponse;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeResponse {
    private Long id;
    private String content;
    private List<TagResponse> tags;
    private Integer commentCount;
    private Integer likeCount;

    @Builder
    public MistakeResponse(Long id, String content, List<TagResponse> tags, Integer commentCount, Integer likeCount) {
        this.id = id;
        this.content = content;
        this.tags = tags;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }
}

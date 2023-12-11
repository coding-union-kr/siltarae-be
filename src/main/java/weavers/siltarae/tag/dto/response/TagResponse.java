package weavers.siltarae.tag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.tag.domain.Tag;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class TagResponse {
    private Long id;
    private String name;

    private Integer mistakeCount;

    @Builder
    public TagResponse(Long id, String name, Integer mistakeCount) {
        this.id = id;
        this.name = name;
        this.mistakeCount = mistakeCount;
    }

    public static TagResponse from(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .mistakeCount(tag.getMistakes().size())
                .build();
    }
}

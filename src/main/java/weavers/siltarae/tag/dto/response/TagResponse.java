package weavers.siltarae.tag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class TagResponse {
    private Long id;
    private String name;

    @Builder
    public TagResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

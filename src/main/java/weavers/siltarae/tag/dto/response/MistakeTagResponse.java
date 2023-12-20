package weavers.siltarae.tag.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.tag.domain.Tag;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeTagResponse {
    private Long id;
    private String name;

    @Builder
    public MistakeTagResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MistakeTagResponse from(Tag tag) {
        return MistakeTagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

}

package weavers.siltarae.mistake.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.global.dto.request.PageRequest;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeListRequest extends PageRequest {
    private String tag;

    public MistakeListRequest(Integer page, Integer size, String tag) {
        super(page, size);
        this.tag = tag;
    }
}

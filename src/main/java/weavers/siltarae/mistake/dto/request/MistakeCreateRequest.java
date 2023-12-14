package weavers.siltarae.mistake.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MistakeCreateRequest {
    private List<Long> tagIds;
    
    @NotBlank(message = "실수 내용은 필수 입니다.")
    @Size(max = 140, message = "실수 내용은 140자를 넘을 수 없습니다.")
    private String content;
}

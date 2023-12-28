package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.tag.dto.response.MistakeTagResponse;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeResponse {
    private Long id;
    private String content;
    private List<MistakeTagResponse> tags;
    private Integer commentCount;
    private Integer likeCount;
    private Long memberId;
    private String memberName;
    private String memberImage;

    @Builder
    public MistakeResponse(Long id, String content, List<MistakeTagResponse> tags, Integer commentCount, Integer likeCount, Long memberId, String memberName, String memberImage) {
        this.id = id;
        this.content = content;
        this.tags = tags;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberImage = memberImage;
    }

    public static MistakeResponse from(Mistake mistake) {
        List<MistakeTagResponse> tag = mistake.getTags().stream().map(
                MistakeTagResponse::from
        ).collect(Collectors.toList());

        return MistakeResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(mistake.getComments().size())
                .likeCount(mistake.getLikes().size())
                .tags(tag)
                .memberId(mistake.getMember().getId())
                .memberName(mistake.getMember().getNickname())
                .memberImage(mistake.getMember().getImageName())
                .build();
    }

}
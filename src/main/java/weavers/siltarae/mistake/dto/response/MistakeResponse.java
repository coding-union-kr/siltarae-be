package weavers.siltarae.mistake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.tag.dto.response.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
public class MistakeResponse {
    private Long id;
    private String content;
    private List<TagResponse> tags;
    private Integer commentCount;
    private Integer likeCount;
    private Long memberId;
    private String memberName;

    @Builder
    public MistakeResponse(Long id, String content, List<TagResponse> tags, Integer commentCount, Integer likeCount, Long memberId, String memberName) {
        this.id = id;
        this.content = content;
        this.tags = tags;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    private static final Integer TEST_COMMENT_COUNT = 13;
    private static final Integer TEST_LIKE_COUNT = 11;

    public static MistakeResponse from(Mistake mistake) {
        List<TagResponse> tag = mistake.getTags().stream().map(
                TagResponse::from
        ).collect(Collectors.toList());

        return MistakeResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(TEST_COMMENT_COUNT)
                .likeCount(TEST_LIKE_COUNT)
                .tags(tag)
                .build();
    }

    public static MistakeResponse peedFrom(Mistake mistake) {
        List<TagResponse> tags = mistake.getTags().stream().map(
                TagResponse::from
        ).collect(Collectors.toList());

        return MistakeResponse.builder()
                .id(mistake.getId())
                .content(mistake.getContent())
                .commentCount(TEST_COMMENT_COUNT)
                .likeCount(mistake.getLikes().size())
                .tags(tag)
                .memberId(mistake.getMember().getId())
                .memberName(mistake.getMember().getNickname())
                .build();
    }
}

package weavers.siltarae.member.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImageResponse {

    private String imageUrl;

    @Builder
    public MemberImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static MemberImageResponse from (String imageUrl) {
        return MemberImageResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }
}

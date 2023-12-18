package weavers.siltarae.login.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import weavers.siltarae.member.domain.SocialType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoResponse {

    private SocialType socialType;

    @JsonProperty("id")
    private String identifier;

    @JsonProperty("given_name")
    private String name;

    @JsonProperty("email")
    private String email;

    public void setSocialType(SocialType socialType) {
        this.socialType = socialType;
    }
}

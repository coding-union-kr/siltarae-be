package weavers.siltarae.login.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private Long memberId;
    private String refreshToken;

    @TimeToLive
    @Value("${jwt.refresh-expiration-time}")
    private Long timeToLive;

    @Builder
    public RefreshToken(Long memberId, String refreshToken, Long timeToLive) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.timeToLive = timeToLive;
    }
}

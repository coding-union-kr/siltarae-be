package weavers.siltarae.login.domain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weavers.siltarae.login.dto.response.TokenPair;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long accessTokenExpirationTime;
    private final Long refreshTokenExpirationTime;

    public JwtProvider(
            @Value("${jwt.secret-key}") final String secretKey,
            @Value("${jwt.access-expiration-time}") final Long accessTokenExpirationTime,
            @Value("${jwt.refresh-expiration-time}") final Long refreshTokenExpirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    public TokenPair createTokenPair(Long memberId) {
        String accessToken = createAccessToken(memberId.toString(), accessTokenExpirationTime);
        String refreshToken = createRefreshToken();

        return TokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

    private String createAccessToken(final String subject, final Long expirationTime) {
        final Date now = new Date();
        final Date expirationDate = new Date(now.getTime()+expirationTime);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefreshToken() {
        return UUID.randomUUID().toString();
    }
}

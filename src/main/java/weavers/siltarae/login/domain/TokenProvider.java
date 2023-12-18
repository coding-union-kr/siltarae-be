package weavers.siltarae.login.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.*;
import weavers.siltarae.login.domain.repository.RefreshTokenRepository;
import weavers.siltarae.login.dto.response.TokenPair;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenProvider {

    private final SecretKey secretKey;
    private final Long accessTokenExpirationTime;
    private final Long refreshTokenExpirationTime;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenProvider(
            @Value("${jwt.secret-key}") final String secretKey,
            @Value("${jwt.access-expiration-time}") final Long accessTokenExpirationTime,
            @Value("${jwt.refresh-expiration-time}") final Long refreshTokenExpirationTime,
            final RefreshTokenRepository refreshTokenRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public TokenPair createTokenPair(final Long memberId) {
        String accessToken = createAccessToken(memberId.toString(), accessTokenExpirationTime);
        String refreshToken = createRefreshToken();

        saveRefreshToken(refreshToken, memberId);

        return TokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

    private String createAccessToken(final String memberId, final Long expirationTime) {
        final Date now = new Date();
        final Date expirationDate = new Date(now.getTime()+expirationTime);

        return Jwts.builder()
                .subject(memberId)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    private String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public String renewAccessToken(final Long memberId) {
        return createAccessToken(memberId.toString(), accessTokenExpirationTime);
    }

    private void saveRefreshToken(final String refreshToken, final Long memberId) {
        RefreshToken createdRefreshToken = RefreshToken.builder()
                .memberId(memberId)
                .refreshToken(refreshToken)
                .build();

        refreshTokenRepository.save(createdRefreshToken);
    }

    public boolean isExpiredAccessAndValidRefresh(final String accessToken, final String refreshToken) {
        try {
            Long memberId = getMemberIdFromAccessToken(accessToken);
            validateRefreshToken(refreshToken, memberId);
        } catch (RefreshTokenException | BadRequestException e) {
            return false;
        }
        return true;
    }

    public boolean isValidTokens(final String accessToken, final String refreshToken) {
        try {
            Long memberId = getMemberIdFromAccessToken(accessToken);
            validateRefreshToken(refreshToken, memberId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void validateRefreshToken(final String token, final Long memberId) {
        RefreshToken refreshToken = refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new RefreshTokenException(ExceptionCode.EXPIRED_REFRESH_TOKEN));

        if(!refreshToken.getRefreshToken().equals(token)) {
            throw new BadRequestException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }
    }

    public Long getMemberIdFromAccessToken(final String accessToken) {
        return Long.parseLong(parseJwt(accessToken).getSubject());
    }

    private Claims parseJwt(final String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            throw new AccessTokenException(ExceptionCode.EXPIRED_ACCESS_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadRequestException(ExceptionCode.INVALID_ACCESS_TOKEN);
        }
    }
}

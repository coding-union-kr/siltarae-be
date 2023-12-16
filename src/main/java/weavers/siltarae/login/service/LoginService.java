package weavers.siltarae.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.login.domain.GoogleProvider;
import weavers.siltarae.login.domain.JwtProvider;
import weavers.siltarae.login.domain.RefreshToken;
import weavers.siltarae.login.domain.repository.RefreshTokenRepository;
import weavers.siltarae.login.dto.response.TokenPair;
import weavers.siltarae.login.dto.response.UserInfoResponse;
import weavers.siltarae.user.domain.User;
import weavers.siltarae.user.domain.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    private final GoogleProvider googleProvider;

    public TokenPair login(String socialType, String code) {
        String authAccessToken = googleProvider.requestAccessToken(code);
        UserInfoResponse userInfo = googleProvider.getUserInfo(authAccessToken);

        User user = userRepository.findByIdentifier(userInfo.getIdentifier())
                .orElseGet(() -> createUser(userInfo));

        TokenPair tokenPair = jwtProvider.createTokenPair(user.getId());

        saveRefreshToken(tokenPair.getRefreshToken(), user.getId());

        return tokenPair;
    }

    public void saveRefreshToken(String refreshToken, Long memberId) {
        RefreshToken createdRefreshToken = RefreshToken.builder()
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build();

        refreshTokenRepository.save(createdRefreshToken);
    }

    private User createUser(UserInfoResponse userInfo) {
        User createdUser = User.builder()
                .identifier(userInfo.getIdentifier())
                .nickname(userInfo.getName())
                .email(userInfo.getEmail())
                .socialType(userInfo.getSocialType())
                .build();
        return userRepository.save(createdUser);
    }
}

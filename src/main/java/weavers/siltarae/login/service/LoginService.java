package weavers.siltarae.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.login.domain.GoogleProvider;
import weavers.siltarae.login.dto.response.UserInfoResponse;
import weavers.siltarae.user.domain.User;
import weavers.siltarae.user.domain.repository.UserRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final GoogleProvider googleProvider;

    public void login(String socialType, String code) {
        String accessToken = googleProvider.requestAccessToken(code);
        UserInfoResponse userInfo = googleProvider.getUserInfo(accessToken);

        User user = userRepository.findByIdentifier(userInfo.getIdentifier())
                .orElse(createUser(userInfo));
    }

    private User createUser(UserInfoResponse userInfo) {
        User createdUser = User.builder()
                .identifier(userInfo.getIdentifier())
                .nickname(userInfo.getName())
                .email(userInfo.getEmail())
                .socialType(userInfo.getSocialType())
                .build();

        log.info("-------user created------");
        log.info("email = {}", createdUser.getEmail());
        log.info("name = {}", createdUser.getNickname());
        return userRepository.save(createdUser);
    }
}

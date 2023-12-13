package weavers.siltarae.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weavers.siltarae.login.domain.GoogleProvider;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final GoogleProvider googleClient;

    public void login(String socialType, String code) {
        String accessToken = googleClient.requestAccessToken(code);
        googleClient.getUserInfo(accessToken);
    }
}

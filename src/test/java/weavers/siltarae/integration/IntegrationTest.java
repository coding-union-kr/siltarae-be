package weavers.siltarae.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import weavers.siltarae.login.domain.TokenProvider;
import weavers.siltarae.login.dto.response.TokenPair;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;

import static weavers.siltarae.member.domain.SocialType.GOOGLE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenProvider tokenProvider;

    public TokenPair tokenPair;

    @BeforeEach
    void setAuth() {
        Member member = Member.builder()
                .email("example@example.com")
                .identifier("123456")
                .nickname("테스트회원")
                .socialType(GOOGLE)
                .build();

        memberRepository.save(member);

        Long memberId = member.getId();
        tokenPair = tokenProvider.createTokenPair(memberId);
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
}

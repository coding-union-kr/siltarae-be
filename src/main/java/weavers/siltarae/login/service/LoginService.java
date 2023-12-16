package weavers.siltarae.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.login.domain.GoogleProvider;
import weavers.siltarae.login.domain.JwtProvider;
import weavers.siltarae.login.domain.RefreshToken;
import weavers.siltarae.login.domain.repository.RefreshTokenRepository;
import weavers.siltarae.login.dto.response.TokenPair;
import weavers.siltarae.login.dto.response.MemberInfoResponse;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    private final GoogleProvider googleProvider;

    public TokenPair login(String socialType, String code) {
        String authAccessToken = googleProvider.requestAccessToken(code);
        MemberInfoResponse memberInfo = googleProvider.getMemberInfo(authAccessToken);

        Member member = memberRepository.findByIdentifier(memberInfo.getIdentifier())
                .orElseGet(() -> createMember(memberInfo));

        TokenPair tokenPair = jwtProvider.createTokenPair(member.getId());

        saveRefreshToken(tokenPair.getRefreshToken(), member.getId());

        return tokenPair;
    }

    public void saveRefreshToken(String refreshToken, Long memberId) {
        RefreshToken createdRefreshToken = RefreshToken.builder()
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build();

        refreshTokenRepository.save(createdRefreshToken);
    }

    private Member createMember(MemberInfoResponse memberInfo) {
        Member createdMember = Member.builder()
                .identifier(memberInfo.getIdentifier())
                .nickname(memberInfo.getName())
                .email(memberInfo.getEmail())
                .socialType(memberInfo.getSocialType())
                .build();
        return memberRepository.save(createdMember);
    }
}

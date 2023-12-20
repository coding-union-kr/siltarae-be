package weavers.siltarae.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.AuthException;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.login.domain.GoogleProvider;
import weavers.siltarae.login.domain.TokenProvider;
import weavers.siltarae.login.dto.response.AccessTokenResponse;
import weavers.siltarae.login.dto.response.TokenPair;
import weavers.siltarae.login.dto.response.MemberInfoResponse;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final GoogleProvider googleProvider;

    private final String BEARER_TYPE = "Bearer";

    public TokenPair login(String socialType, String code) {
        String authAccessToken = googleProvider.requestAccessToken(code);
        MemberInfoResponse memberInfo = googleProvider.getMemberInfo(authAccessToken);

        Member member = memberRepository.findByIdentifier(memberInfo.getIdentifier())
                .orElseGet(() -> createMember(memberInfo));

        return tokenProvider.createTokenPair(member.getId());
    }

    public AccessTokenResponse renewAccessToken(String authorizationHeader, String refreshToken) {
        final String accessToken = getAccessTokenFromHeader(authorizationHeader);

        if(!tokenProvider.isExpiredAccessAndValidRefresh(accessToken, refreshToken)) {
            logout(refreshToken);
            throw new AuthException(ExceptionCode.FAIL_TO_VALIDATE_TOKEN);
        }

        Long memberId = tokenProvider.getMemberIdFromAccessToken(accessToken);
        String newAccessToken = tokenProvider.renewAccessToken(memberId);

        return AccessTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    private String getAccessTokenFromHeader(String authorizationHeader) {
        if(authorizationHeader==null || !authorizationHeader.startsWith(BEARER_TYPE)) {
            throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
        }
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
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

    public void logout(String refreshToken) {
        tokenProvider.deleteRefreshToken(refreshToken);
    }
}

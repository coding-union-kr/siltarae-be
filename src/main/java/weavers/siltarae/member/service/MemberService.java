package weavers.siltarae.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.login.domain.TokenProvider;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;
import weavers.siltarae.member.dto.MemberResponse;
import weavers.siltarae.member.dto.MemberUpdateRequest;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public MemberResponse updateMember(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));

        member.update(request.getNickname());

        return MemberResponse.from(member);
    }

    public void deleteMember(Long memberId, String refreshToken) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));
        member.delete();

        tokenProvider.deleteRefreshToken(refreshToken);
    }
}

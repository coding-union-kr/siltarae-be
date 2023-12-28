package weavers.siltarae.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.image.ImageUtil;
import weavers.siltarae.global.image.domain.Image;
import weavers.siltarae.login.domain.TokenProvider;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;
import weavers.siltarae.member.dto.response.MemberInfoResponse;
import weavers.siltarae.member.dto.response.MemberNicknameResponse;
import weavers.siltarae.member.dto.request.MemberUpdateRequest;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final ImageUtil imageUtil;

    @Value("${cloud.aws.s3.folder.member}")
    private String folder;

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));

        return MemberInfoResponse.from(member);
    }

    public String uploadMemberImage(Long memberId, MultipartFile file) {
        Image image = new Image(file);
        String imageUrl = imageUtil.uploadImage(folder, image);

        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));
        member.updateImage(imageUrl);

        return imageUrl;
    }

    public void deleteMemberImage(Long memberId) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));

        if(member.hasDefaultImage()) return;

        imageUtil.deleteImage(folder, member.getImageName());
    }

    public MemberNicknameResponse changeMemberNickname(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));

        member.updateNickname(request.getNickname());

        return MemberNicknameResponse.from(member);
    }

    public void deleteMember(Long memberId, String refreshToken) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));

        if(!member.hasDefaultImage()) {
            imageUtil.deleteImage(folder, member.getImageName());
        }
        member.delete();

        tokenProvider.deleteRefreshToken(refreshToken);
    }
}

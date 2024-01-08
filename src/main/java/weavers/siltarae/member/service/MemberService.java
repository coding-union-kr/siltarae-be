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
import weavers.siltarae.member.dto.response.MemberImageResponse;
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

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = findMember(memberId);

        return MemberInfoResponse.from(member);
    }

    public MemberImageResponse updateMemberImage(Long memberId, MultipartFile file) {
        Member member = findMember(memberId);

        String imageUrl;
        if (file == null || file.isEmpty()) {
            imageUrl = member.getDefaultImageUrl();
        } else {
            imageUrl = uploadMemberImage(file);
        }

        deleteMemberImage(member);
        member.updateImage(imageUrl);

        return MemberImageResponse.from(imageUrl);
    }

    private String uploadMemberImage(MultipartFile file) {

        Image image = new Image(file);
        return imageUtil.uploadImage(folder, image);
    }

    private void deleteMemberImage(Member member) {
        if(member.hasDefaultImage()) return;

        imageUtil.deleteImage(folder, member.getImageName());
    }

    public MemberNicknameResponse changeMemberNickname(Long memberId, MemberUpdateRequest request) {
        Member member = findMember(memberId);

        member.updateNickname(request.getNickname());

        return MemberNicknameResponse.from(member);
    }

    public void deleteMember(Long memberId, String refreshToken) {
        Member member = findMember(memberId);

        if(!member.hasDefaultImage()) {
            imageUtil.deleteImage(folder, member.getImageName());
        }
        member.delete();

        tokenProvider.deleteRefreshToken(refreshToken);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER));
    }
}

package weavers.siltarae.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.exception.ExceptionCode;
import weavers.siltarae.like.domain.Likes;
import weavers.siltarae.like.domain.repository.LikeRepository;
import weavers.siltarae.member.domain.Member;
import weavers.siltarae.member.domain.repository.MemberRepository;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.repository.MistakeRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final MemberRepository memberRepository;
    private final MistakeRepository mistakeRepository;
    private final LikeRepository likeRepository;


    public void like(Long memberId, Long mistakeId) {
        Member member = getTestUser(memberId);
        Mistake mistake = getMistake(mistakeId);

        toggleLike(member, mistake);
    }

    private void toggleLike(Member member, Mistake mistake) {
        likeRepository.findByMemberAndMistake(member, mistake)
                .ifPresentOrElse(
                        this::deleteLike,                   // Like 가 존재한다면 deleteLike
                        () -> addLike(member, mistake)      // Like 가 존재하지 않는다면 addLike
                );
    }

    private void addLike(Member member, Mistake mistake) {
        likeRepository.save(
                Likes.builder()
                    .member(member)
                    .mistake(mistake)
                .build());
    }

    private void deleteLike(Likes likes) {
        likeRepository.delete(likes);
    }

    private Mistake getMistake(Long mistakeId) {
        return mistakeRepository.findById(mistakeId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MISTAKE));
    }

    private Member getTestUser(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_USER));
    }
}

package weavers.siltarae.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import weavers.siltarae.like.domain.repository.LikeRepository;

@Component
@RequiredArgsConstructor
public class LikeAbleUtil {
    private final LikeRepository likeRepository;

    public Boolean getLikeAble(Long mistakeId, Long memberId) {
        if (memberId == null) {
            return false;
        }

        return likeRepository.existsByMistake_IdAndMember_Id(mistakeId, memberId);
    }

}

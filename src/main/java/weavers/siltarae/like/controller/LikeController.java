package weavers.siltarae.like.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.like.service.LikeService;
import weavers.siltarae.login.Auth;

@Tag(name = "[좋아요] 좋아요 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> addLike(
            @Auth Long memberId,
            @RequestParam("mistakeId") Long mistakeId) {
        likeService.like(memberId, mistakeId);

        return ResponseEntity.ok().build();
    }

}

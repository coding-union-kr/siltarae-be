package weavers.siltarae.like.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.like.service.LikeService;

@Tag(name = "[좋아요] 좋아요 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private static final Long TEST_USER_ID = 1L;
    private final LikeService likeService;
    @PostMapping
    public ResponseEntity<Void> addLike(
            @RequestParam("mistakeId") Long mistakeId) {
        likeService.like(TEST_USER_ID, mistakeId);

        return ResponseEntity.ok().build();
    }

}

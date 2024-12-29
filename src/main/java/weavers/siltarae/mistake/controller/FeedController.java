package weavers.siltarae.mistake.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weavers.siltarae.login.Auth;
import weavers.siltarae.mistake.dto.request.FeedRequest;
import weavers.siltarae.mistake.dto.response.FeedListResponse;
import weavers.siltarae.mistake.service.FeedService;

@Tag(name = "[피드] 피드 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {
    private final FeedService feedService;

    @GetMapping
    @Operation(summary = "피드 조회")
    public ResponseEntity<FeedListResponse> getFeeds(
            @Valid FeedRequest request,
            @Auth Long memberId) {

        return ResponseEntity.ok(feedService.getFeedList(request, memberId));
    }

}

package weavers.siltarae.mistake.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weavers.siltarae.mistake.dto.request.PeedRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.service.PeedService;

@Tag(name = "[피드] 피드 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/peed")
public class PeedController {
    private final PeedService peedService;

    @GetMapping
    @Operation(summary = "피드 조회")
    public ResponseEntity<MistakeListResponse> getPeeds(
            @Valid PeedRequest request) {

        return ResponseEntity.ok(peedService.getPeedList(request));
    }

}

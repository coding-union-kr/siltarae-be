package weavers.siltarae.mistake.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.login.Auth;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
import weavers.siltarae.mistake.dto.request.MistakeListRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.dto.response.MistakeResponse;
import weavers.siltarae.mistake.service.MistakeService;

import java.net.URI;
import java.util.List;

@Tag(name = "[실수] 실수 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mistakes")
public class MistakeController {
    private final MistakeService mistakeService;

    @PostMapping
    public ResponseEntity<Void> createMistake(
            @Auth Long memberId,
            @RequestBody @Valid MistakeCreateRequest request) {
        Long mistakeId = mistakeService.save(request, memberId);

        return ResponseEntity.created(URI.create("mistakes/" + mistakeId)).build();
    }

    @GetMapping
    public ResponseEntity<MistakeListResponse> getMistakes(@Auth Long memberId, MistakeListRequest request) {
        return ResponseEntity.ok(mistakeService.getMistakeList(memberId, request));
    }

    @GetMapping("/{mistakeId}")
    public ResponseEntity<MistakeResponse> getMistake(
            @PathVariable("mistakeId") Long mistakeId) {
        return ResponseEntity.ok(mistakeService.getMistake(mistakeId));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteMistake(
            @Auth Long memberId,
            @RequestBody List<Long> ids) {
        mistakeService.deleteMistake(memberId, ids);

        return ResponseEntity.noContent().build();
    }
}

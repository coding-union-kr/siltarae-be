package weavers.siltarae.mistake.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.global.dto.request.PageRequest;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
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

    private static final Long TEST_USER_ID = 1L;

    @PostMapping("")
    public ResponseEntity<Void> createMistake(
            @RequestBody @Valid MistakeCreateRequest request) {
        Long mistakeId = mistakeService.save(request, TEST_USER_ID);

        return ResponseEntity.created(URI.create("mistakes/" + mistakeId)).build();
    }

    @GetMapping
    public ResponseEntity<MistakeListResponse> getMistakes(PageRequest request) {
        return ResponseEntity.ok(mistakeService.getMistakeList(TEST_USER_ID, request.toPageable()));
    }

    @GetMapping("/{mistakeId}")
    public ResponseEntity<MistakeResponse> getMistake(
            @PathVariable("mistakeId") Long mistakeId) {
        return ResponseEntity.ok(mistakeService.getMistake(TEST_USER_ID, mistakeId));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteMistake(
            @RequestBody List<Long> ids) {
        mistakeService.deleteMistake(TEST_USER_ID, ids);

        return ResponseEntity.noContent().build();
    }
}

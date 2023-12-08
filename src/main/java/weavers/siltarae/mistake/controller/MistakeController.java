package weavers.siltarae.mistake.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.mistake.dto.request.MistakeCreateRequest;
import weavers.siltarae.mistake.dto.response.MistakeListResponse;
import weavers.siltarae.mistake.service.MistakeService;

import java.net.URI;

@Tag(name = "[실수] 실수 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/mistakes")
public class MistakeController {
    private final MistakeService mistakeService;

    private static final Long TEST_USER_ID = 1L;

    @PostMapping("")
    public ResponseEntity<?> createMistake(
            @RequestBody @Valid MistakeCreateRequest request) {
        Long mistakeId = mistakeService.save(request, TEST_USER_ID);

        return ResponseEntity.created(URI.create("mistakes/" + mistakeId)).build();
    }

    @GetMapping
    public ResponseEntity<MistakeListResponse> getMistakes(Pageable pageable) {
        return ResponseEntity.ok(mistakeService.getMistakeList(TEST_USER_ID, pageable));
    }
}

package weavers.siltarae.tag.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.tag.service.TagService;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class TagController {

    private final Long TEMP_USER_ID = 1L;

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody @Valid final TagCreateRequest request) {
        tagService.save(TEMP_USER_ID, request);
        return ResponseEntity.noContent().build();
    }
}

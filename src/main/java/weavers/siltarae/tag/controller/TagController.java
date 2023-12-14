package weavers.siltarae.tag.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.tag.dto.response.TagListResponse;
import weavers.siltarae.tag.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final Long TEMP_USER_ID = 1L;

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody @Valid final TagCreateRequest request) {
        tagService.save(TEMP_USER_ID, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<TagListResponse> getTagList() {
        TagListResponse tagListResponse = tagService.getTagList(TEMP_USER_ID);

        return ResponseEntity.ok(tagListResponse);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteTags(@RequestBody final List<Long> tagIdList) {
        tagService.deleteTags(tagIdList);

        return ResponseEntity.noContent().build();
    }
}

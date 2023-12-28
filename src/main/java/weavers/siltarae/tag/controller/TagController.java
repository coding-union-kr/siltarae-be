package weavers.siltarae.tag.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.login.Auth;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.tag.dto.response.TagListResponse;
import weavers.siltarae.tag.dto.response.TagResponse;
import weavers.siltarae.tag.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @Auth Long memberId,
            @RequestBody @Valid final TagCreateRequest request) {
        TagResponse response = tagService.save(memberId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<TagListResponse> getTagList(@Auth Long memberId) {
        TagListResponse tagListResponse = tagService.getTagList(memberId);

        return ResponseEntity.ok(tagListResponse);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteTags(@Auth Long memberId, @RequestBody final List<Long> tagIdList) {
        tagService.deleteTags(memberId, tagIdList);

        return ResponseEntity.noContent().build();
    }
}

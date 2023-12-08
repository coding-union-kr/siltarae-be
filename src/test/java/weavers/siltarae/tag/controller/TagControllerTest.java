package weavers.siltarae.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import weavers.siltarae.tag.dto.request.TagCreateRequest;
import weavers.siltarae.tag.service.TagService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    @Test
    void 태그_등록_요청을_성공한다() throws Exception {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest("tag_Name");
        

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagCreateRequest)));

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    void 태그명_10자_초과_시_예외가_발생한다() throws Exception {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest("VeryLongTagName");

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagCreateRequest)));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("태그명은 최대 10자입니다."));
    }

    @Test
    void 태그명에_특수문자가_있으면_예외가_발생한다() throws Exception {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest("Tag$name");

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagCreateRequest)));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("태그명에는 한글, 영어, 숫자, _만 사용 가능합니다."));
    }
}
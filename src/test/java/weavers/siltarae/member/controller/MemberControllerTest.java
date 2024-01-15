package weavers.siltarae.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import weavers.siltarae.global.ControllerTest;
import weavers.siltarae.member.dto.request.MemberUpdateRequest;
import weavers.siltarae.member.service.MemberService;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ControllerTest{

    @MockBean
    MemberService memberService;

    @Test
    void 닉네임_10자_초과_시_예외가_발생한다() throws Exception {
        // given
        MemberUpdateRequest request = new MemberUpdateRequest("10자를초과하는닉네임");

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/member")
                .header(AUTHORIZATION, ACCESS_TOKEN)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("닉네임은 최대 10자입니다."));
    }

    @Test
    void 닉네임에_특수문자가_있으면_예외가_발생한다() throws Exception {
        // given
        MemberUpdateRequest request = new MemberUpdateRequest("특!수한닉네임");

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/member")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("닉네임은 한글, 영어, 숫자만 사용 가능합니다."));
    }


}

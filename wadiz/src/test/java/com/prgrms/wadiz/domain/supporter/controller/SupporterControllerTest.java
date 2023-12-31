package com.prgrms.wadiz.domain.supporter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.service.SupporterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupporterController.class)
class SupporterControllerTest {

    @MockBean
    private SupporterService supporterService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private Supporter supporter;

    private final String code = "$.[?(@.code == '%s')]";

    private final String message = "$.[?(@.message == '%s')]";

    @BeforeEach
    void setUp() {
        supporter = Supporter.builder()
                .supporterName("test")
                .supporterEmail("test")
                .build();
    }

    @Test
    @DisplayName("[성공] 서포터 회원가입 테스트")
    void supporterSignUpTest() throws Exception {
        //given
        SupporterCreateRequestDTO createRequestDTO = SupporterCreateRequestDTO.builder()
                .supporterName(supporter.getSupporterName())
                .supporterEmail(supporter.getSupporterEmail())
                .build();

        String json = mapper.writeValueAsString(createRequestDTO);

        //when & then
        mvc.perform(post("/supporters/sign-up")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 서포터 수정 테스트")
    void supporterUpdateTest() throws Exception {
        //given
        SupporterUpdateRequestDTO updateRequestDTO = SupporterUpdateRequestDTO.builder()
                .supporterName(supporter.getSupporterName())
                .supporterEmail(supporter.getSupporterEmail())
                .build();

        String json = mapper.writeValueAsString(updateRequestDTO);

        //when & then
        mvc.perform(put("/supporters/" + 1L)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());

    }

    @Test
    @DisplayName("[성공] 서포터 삭제 테스트")
    void supporterDeleteTest() throws Exception {
        // when & then
        mvc.perform(delete("/supporters/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 서포터 생성 실패 테스트")
    void invalidEmailTest() throws Exception {
        //given
        SupporterCreateRequestDTO dto = SupporterCreateRequestDTO.builder()
                .supporterName(supporter.getSupporterName())
                .supporterEmail("aaa")
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/supporters/sign-up")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("이름은 최소 2자 이상입니다.","이메일 형식이 맞지 않습니다.");
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 서포터 생성 실패 테스트")
    void invalidNameTest() throws Exception {
        //given
        SupporterCreateRequestDTO dto = SupporterCreateRequestDTO.builder()
                .supporterName(" ")
                .supporterEmail(supporter.getSupporterEmail())
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/supporters/sign-up")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("이름은 최소 2자 이상입니다.");
    }

}
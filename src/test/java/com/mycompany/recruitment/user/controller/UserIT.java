package com.mycompany.recruitment.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.Response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserIT {

    @MockBean
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {
        // Given
        GithubUserResponse githubResponse = GithubUserResponse.builder()
                .id("583231")
                .login("testUser")
                .name("test user")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt(LocalDateTime.now())
                .followers(5)
                .publicRepos(5)
                .build();

        UserResponse expectedResult = UserResponse.builder()
                .id("583231")
                .login("testUser")
                .name("test user")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt(LocalDateTime.now())
                .calculations(null)
                .build();

        ResponseEntity<GithubUserResponse> responseEntity = new ResponseEntity<>(
                githubResponse,
                HttpStatusCode.valueOf(200)
        );

        // When
        when(restTemplate.getForEntity("https://api.github.com/users/testUser", GithubUserResponse.class)).thenReturn(responseEntity);

        String responseBody = mvc.perform(get("/users/testUser"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResult));
    }

}

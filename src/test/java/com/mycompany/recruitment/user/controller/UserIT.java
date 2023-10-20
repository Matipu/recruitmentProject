package com.mycompany.recruitment.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserIT {

    @MockBean
    private WebClient mockedWebClient;

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

        // When
        createMockWebClient("https://api.github.com/users/testUser", githubResponse);

        String responseBody = mvc.perform(get("/users/testUser"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResult));
    }

    void createMockWebClient(String uri, Object responseDTO) {
        // Create the mock objects
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestBodySpec requestBodyMock = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        ClientResponse responseMock = mock(ClientResponse.class);

        // Configure the mock objects
        when(mockedWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(uri)).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(any(Class.class))).thenReturn(Mono.just(responseDTO));
        when(responseMock.bodyToMono(any(Class.class))).thenReturn(Mono.just(responseDTO));
    }
}

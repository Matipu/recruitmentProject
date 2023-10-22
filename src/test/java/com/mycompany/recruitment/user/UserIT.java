package com.mycompany.recruitment.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.dto.UserDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
    public void getUserWhenUserIsExist()
            throws Exception {
        // Given
        LocalDateTime createdAt = LocalDateTime.now();
        GithubUserResponse githubResponse = GithubUserResponse.builder()
                .id("583231")
                .login("testUser")
                .name("test user")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt(createdAt)
                .followers(5L)
                .publicRepos(5L)
                .build();

      UserDto expectedResult = UserDto.builder()
                .id("583231")
                .login("testUser")
                .name("test user")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt(createdAt)
                .calculations(8.4)
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

  @Test
  public void shouldThrowBusinessExceptionWhenFollowersIsZero()
      throws Exception {
    // Given
    LocalDateTime createdAt = LocalDateTime.now();
    GithubUserResponse githubResponse = GithubUserResponse.builder()
        .id("583231")
        .login("testUser")
        .name("test user")
        .type("User")
        .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
        .createdAt(createdAt)
        .followers(0L)
        .publicRepos(5L)
        .build();

    ResponseEntity<GithubUserResponse> responseEntity = new ResponseEntity<>(
        githubResponse,
        HttpStatusCode.valueOf(200)
    );

    // When
    when(restTemplate.getForEntity("https://api.github.com/users/testUser", GithubUserResponse.class)).thenReturn(responseEntity);

    mvc.perform(get("/users/testUser"))
        // Then
        .andExpect(status().is(400));
  }

  @Test
  public void shouldThrowNotFoundException() throws Exception {
    // When
    when(restTemplate.getForEntity("https://api.github.com/users/testUser", GithubUserResponse.class))
        .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", new HttpHeaders(), null, null));

    mvc.perform(get("/users/testUser"))
        // Then
        .andExpect(status().is(400));
  }

  @Test
  public void shouldThrowTechnicalExceptionWhenServiceInUnavailable() throws Exception {
    // When
    when(restTemplate.getForEntity("https://api.github.com/users/testUser", GithubUserResponse.class))
        .thenThrow(HttpClientErrorException.create(HttpStatus.SERVICE_UNAVAILABLE, "Service unavaible", new HttpHeaders(), null, null));

    mvc.perform(get("/users/testUser"))
        // Then
        .andExpect(status().is(500));
  }
}

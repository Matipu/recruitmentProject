package com.mycompany.recruitment.integrations.github.api.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.exception.BusinessException;
import com.mycompany.recruitment.user.exception.TechnicalException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class GithubUserConsumerTest {

  @InjectMocks
  GithubUserConsumer githubUserConsumer;

  @Mock
  RestTemplate restTemplate;

  @Test
  void shouldGetAndMapUser() {
    // Given
    GithubUserResponse githubResponse = GithubUserResponse.builder().id("583231").login("testLogin").name("test user").type("User")
        .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4").createdAt(LocalDateTime.now()).followers(5L).publicRepos(5L).build();

    ResponseEntity<GithubUserResponse> responseEntity = new ResponseEntity<>(githubResponse, HttpStatusCode.valueOf(200));

    // When
    when(restTemplate.getForEntity("https://api.github.com/users/testLogin", GithubUserResponse.class)).thenReturn(responseEntity);

    GithubUserResponse result = githubUserConsumer.getUser("testLogin");

    // Then
    assertThat(githubResponse).usingRecursiveComparison().isEqualTo(result);
  }

  @Test
  void shouldThrowBusinessExceptionWhenUserNotFound() {
    // When
    when(restTemplate.getForEntity("https://api.github.com/users/testLogin", GithubUserResponse.class)).thenThrow(
        HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", new HttpHeaders(), null, null));

    // Then
    assertThrows(BusinessException.class, () -> githubUserConsumer.getUser("testLogin"));
  }

  @Test
  void shouldThrowBusinessExceptionWhenServiceInUnavailable() {
    // When
    when(restTemplate.getForEntity("https://api.github.com/users/testLogin", GithubUserResponse.class)).thenThrow(
        HttpClientErrorException.create(HttpStatus.SERVICE_UNAVAILABLE, "Service unavaible", new HttpHeaders(), null, null));

    // Then
    assertThrows(TechnicalException.class, () -> githubUserConsumer.getUser("testLogin"));
  }
}

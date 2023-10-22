package com.mycompany.recruitment.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.recruitment.integrations.github.api.consumer.GithubUserConsumer;
import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.dto.UserDto;
import com.mycompany.recruitment.user.mapper.UserMapperImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  UserService userService;
  @Mock
  GithubUserConsumer githubUserConsumer;
  @Mock
  UserStatisticService userStatisticService;
  @Spy
  UserMapperImpl userMapperImpl;

  @Test
  void shouldGetAndMapUser() {
    // Given
    LocalDateTime createdAt = LocalDateTime.now();
    UserDto expectedResult = UserDto.builder()
        .id("583231")
        .login("testLogin")
        .name("test user")
        .type("User")
        .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
        .createdAt(createdAt)
        .calculations(8.4)
        .build();

    GithubUserResponse githubResponse = GithubUserResponse.builder()
        .id("583231")
        .login("testLogin")
        .name("test user")
        .type("User")
        .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
        .createdAt(createdAt)
        .followers(5L)
        .publicRepos(5L)
        .build();

    Mockito.when(githubUserConsumer.getUser("testLogin")).thenReturn(githubResponse);

    // When
    UserDto response = userService.getUser("testLogin");

    // Then
    assertThat(response)
        .usingRecursiveComparison()
        .isEqualTo(expectedResult);
  }
}
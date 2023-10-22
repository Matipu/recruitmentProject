package com.mycompany.recruitment.user.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.recruitment.user.dto.UserDto;
import com.mycompany.recruitment.user.service.UserService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void shouldCallGetUserMethodAndReturnCorrectValue() {
        // Given
      UserDto userDto = UserDto.builder()
                .id("583231")
                .login("octocat")
                .name("The Octocat")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt(LocalDateTime.now())
                .calculations(10.0)
                .build();

      Mockito.when(userService.getUser("testLogin")).thenReturn(userDto);

        // When
      UserDto response = userController.getUser("testLogin");

        // Then
        assertThat(response)
                .usingRecursiveComparison()
            .isEqualTo(userDto);
    }

}
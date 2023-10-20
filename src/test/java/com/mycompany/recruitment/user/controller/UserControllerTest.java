package com.mycompany.recruitment.user.controller;

import com.mycompany.recruitment.user.response.UserResponse;
import com.mycompany.recruitment.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void shouldGetUserMethodCallServiceAndReturnCorrectValue() {
        // Given
        UserResponse userResponse = UserResponse.builder()
                .id("583231")
                .login("octocat")
                .name("The Octocat")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt(LocalDateTime.now())
                .calculations(BigDecimal.TEN)
                .build();

        Mockito.when(userService.getUser("testLogin")).thenReturn(userResponse);

        // When
        UserResponse response = userController.getUser("testLogin");

        // Then
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(userResponse);
    }

}
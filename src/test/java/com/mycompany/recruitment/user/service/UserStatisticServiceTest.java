package com.mycompany.recruitment.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import com.mycompany.recruitment.user.entity.UserStatistic;
import com.mycompany.recruitment.user.repository.UserStatisticRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserStatisticServiceTest {

  @InjectMocks
  UserStatisticService userStatisticService;
  @Mock
  UserStatisticRepository userStatisticRepository;

  @Test
  void shouldCreateUserStatistic() {
    // Given
    Mockito.when(userStatisticRepository.findById("testLogin")).thenReturn(Optional.empty());

    // When
    userStatisticService.updateUserStatistic("testLogin");

    // Then
    verify(userStatisticRepository).save(argThat(x -> {
          assertThat(x.getLogin()).isNotNull();
          assertThat(x.getLogin()).isEqualTo("testLogin");
          assertThat(x.getRequestCount()).isEqualTo(1);
          return true;
        }
    ));
  }

  @Test
  void shouldUpdateUserStatistic() {
    // Given
    Mockito.when(userStatisticRepository.findById("testLogin")).thenReturn(Optional.of(new UserStatistic("testLogin", 1, 1)));

    // When
    userStatisticService.updateUserStatistic("testLogin");

    // Then
    verify(userStatisticRepository).save(argThat(x -> {
          assertThat(x.getLogin()).isNotNull();
          assertThat(x.getLogin()).isEqualTo("testLogin");
          assertThat(x.getRequestCount()).isEqualTo(2);
          return true;
        }
    ));
  }
}

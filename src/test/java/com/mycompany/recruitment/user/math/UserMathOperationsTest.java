package com.mycompany.recruitment.user.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.mycompany.recruitment.user.exception.BusinessException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserMathOperationsTest {

  private final static double EPSILON = 0.00001;

  private static Stream<Arguments> calculationData() {
    return Stream.of(arguments(5L, 5L, 8.4), arguments(7L, 7L, 7.71428571));
  }

  @Test
  void shouldThrowExceptionWhenFollowersIsZero() {
    // When
    // Then
    assertThrows(BusinessException.class, () -> {
      UserMathOperations.calculations(0L, 5L);
    });
  }

  @ParameterizedTest
  @MethodSource("calculationData")
  void shouldCalculateData(Long followers, Long publicRepos, double expectedValue) {
    // When
    double result = UserMathOperations.calculations(followers, publicRepos);

    // Then
    assertEquals(result, expectedValue, EPSILON);
  }
}
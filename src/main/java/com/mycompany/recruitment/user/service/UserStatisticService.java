package com.mycompany.recruitment.user.service;

import com.mycompany.recruitment.user.entity.UserStatistic;
import com.mycompany.recruitment.user.repository.UserStatisticRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatisticService {

  private final static Integer DEFAULT_REQUEST_COUNT = 1;
  private final static Integer DEFAULT_VERSION = 1;

  private final UserStatisticRepository userStatisticRepository;

  public void updateUserStatistic(String login) {
    Optional<UserStatistic> userStatistic = userStatisticRepository.findById(login);
    userStatistic.ifPresentOrElse(
        this::updateUserStatistic,
        () -> createUserStatistic(login));
  }

  private void createUserStatistic(String login) {
    userStatisticRepository.save(new UserStatistic(login, DEFAULT_REQUEST_COUNT, DEFAULT_VERSION));
  }

  private void updateUserStatistic(UserStatistic userStatistic) {
    userStatistic.incrementRequestCount();
    userStatisticRepository.save(userStatistic);
  }
}

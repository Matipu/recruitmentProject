package com.mycompany.recruitment.user.service;

import com.mycompany.recruitment.integrations.github.api.consumer.GithubUserConsumer;
import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.dto.UserDto;
import com.mycompany.recruitment.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final GithubUserConsumer githubUserConsumer;
  private final UserMapper userMapper;
  private final UserStatisticService userStatisticService;

  public UserDto getUser(String login) {
    GithubUserResponse githubUser = githubUserConsumer.getUser(login);
    userStatisticService.updateUserStatistic(login);

    return userMapper.mapGithubUser(githubUser);
  }
}

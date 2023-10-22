package com.mycompany.recruitment.integrations.github.api.consumer;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.exception.BusinessException;
import com.mycompany.recruitment.user.exception.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubUserConsumer {

  static final String GITHUB_USER_URL = "https://api.github.com/users/";

  private final RestTemplate restTemplate;

  public GithubUserResponse getUser(@PathVariable String login) {
    try {
      return restTemplate.getForEntity(GITHUB_USER_URL + login, GithubUserResponse.class).getBody();
    } catch (HttpStatusCodeException exception) {
      int statusCode = exception.getStatusCode().value();
      log.error("An error has occured: Response code " + statusCode);
      if (NOT_FOUND.value() == statusCode) {
        throw new BusinessException("User " + login + " not found.");
      } else {
        throw new TechnicalException("Unexpected github exception for login: " + login);
      }
    }
  }
}

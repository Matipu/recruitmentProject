package com.mycompany.recruitment.integrations.github.api.consumer;

import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GithubUserConsumer {

    static final String GITHUB_USER_URL = "https://api.github.com/users/";

    private final RestTemplate restTemplate;

    public GithubUserResponse getUser(@PathVariable String login) {
        ResponseEntity<GithubUserResponse> response = restTemplate.getForEntity(GITHUB_USER_URL + login, GithubUserResponse.class);
        return response.getBody();
    }
}

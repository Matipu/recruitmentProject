package com.mycompany.recruitment.integrations.github.api.consumer;

import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GithubUserConsumer {

    static final String GITHUB_USER_URL = "https://api.github.com/";
    private final WebClient webClient;

    public GithubUserResponse getUser(@PathVariable String login) {
        return webClient.get().uri("/users/" + login)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<GithubUserResponse>() {
                })
                .block();
    }
}

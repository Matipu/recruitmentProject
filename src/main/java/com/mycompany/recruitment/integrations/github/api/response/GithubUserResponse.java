package com.mycompany.recruitment.integrations.github.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GithubUserResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("login")
    private String login;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("followers")
    private Integer followers;

    @JsonProperty("public_repos")
    private Integer publicRepos;
}

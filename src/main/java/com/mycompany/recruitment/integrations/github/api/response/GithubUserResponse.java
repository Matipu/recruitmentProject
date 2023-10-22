package com.mycompany.recruitment.integrations.github.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GithubUserResponse {

  private String id;

  private String login;

  private String name;

  private String type;

  private Long followers;

  @JsonProperty("avatar_url")
  private String avatarUrl;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonProperty("public_repos")
  private Long publicRepos;
}

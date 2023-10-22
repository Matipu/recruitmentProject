package com.mycompany.recruitment.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserDto {

  String id;
  String login;
  String name;
  String type;
  String avatarUrl;
  LocalDateTime createdAt;
  Double calculations;
}

package com.mycompany.recruitment.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserResponse {

    String id;
    String login;
    String name;
    String type;
    String avatarUrl;
    LocalDateTime createdAt;
    Double calculations;

}

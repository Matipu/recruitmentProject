package com.mycompany.recruitment.user.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    BigDecimal calculations;

}

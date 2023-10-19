package com.mycompany.recruitment.user.service;

import com.mycompany.recruitment.user.Response.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;

public class UserService {

    public UserResponse getUser(@PathVariable String login) {
        return new UserResponse();
    }
}

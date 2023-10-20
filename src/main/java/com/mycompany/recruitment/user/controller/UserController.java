package com.mycompany.recruitment.user.controller;

import com.mycompany.recruitment.user.Response.UserResponse;
import com.mycompany.recruitment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    public UserResponse getUser(@PathVariable String login) {
        return userService.getUser(login);
    }
}

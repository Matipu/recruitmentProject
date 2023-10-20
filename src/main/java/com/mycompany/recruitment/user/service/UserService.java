package com.mycompany.recruitment.user.service;

import com.mycompany.recruitment.integrations.github.api.consumer.GithubUserConsumer;
import com.mycompany.recruitment.user.Response.UserResponse;
import com.mycompany.recruitment.user.mapper.UserMapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GithubUserConsumer githubUserConsumer;
    private final UserMapper userMapper;

    public UserResponse getUser(@PathVariable String login) {
        return userMapper.mapGithubUser(githubUserConsumer.getUser(login));
    }
}

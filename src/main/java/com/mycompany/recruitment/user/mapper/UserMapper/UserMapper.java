package com.mycompany.recruitment.user.mapper.UserMapper;

import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.Response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse mapGithubUser(GithubUserResponse user);

}

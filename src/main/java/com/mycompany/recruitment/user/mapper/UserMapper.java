package com.mycompany.recruitment.user.mapper;

import com.mycompany.recruitment.integrations.github.api.response.GithubUserResponse;
import com.mycompany.recruitment.user.math.UserMathOperations;
import com.mycompany.recruitment.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {UserMathOperations.class})
public interface UserMapper {


    @Mapping(target = "calculations", expression = "java(UserMathOperations.calculations(user.getFollowers(), user.getPublicRepos()))")

    UserResponse mapGithubUser(GithubUserResponse user);

}

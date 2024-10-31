package com.project.crmbackend.mapper;

import com.project.crmbackend.dto.request.UserRequest;
import com.project.crmbackend.dto.response.UserResponse;
import com.project.crmbackend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User requestToEntity(UserRequest userRequest);

    UserResponse entityToResponse(User user);
}

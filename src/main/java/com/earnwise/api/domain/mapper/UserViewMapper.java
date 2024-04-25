package com.earnwise.api.domain.mapper;

import com.earnwise.api.domain.dto.UserView;
import com.earnwise.api.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserViewMapper {
    UserView toUserView(User user);
}

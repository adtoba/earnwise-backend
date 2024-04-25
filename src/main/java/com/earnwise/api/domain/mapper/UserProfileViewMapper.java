package com.earnwise.api.domain.mapper;

import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.model.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileViewMapper {
    UserProfileView toUserProfileView(UserProfile userProfile);
}

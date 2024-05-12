package com.earnwise.api.domain.mapper;

import com.earnwise.api.domain.dto.ExpertProfileView;
import com.earnwise.api.domain.model.ExpertProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpertProfileViewMapper {
    ExpertProfileView toExpertProfileView(ExpertProfile expertProfile);
}

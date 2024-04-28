package com.earnwise.api.domain.mapper;

import com.earnwise.api.domain.dto.SocialsView;
import com.earnwise.api.domain.model.Socials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocialsViewMapper {
    SocialsView toSocialsView(Socials socials);
}

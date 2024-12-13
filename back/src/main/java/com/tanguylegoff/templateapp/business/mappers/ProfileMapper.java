package com.tanguylegoff.templateapp.business.mappers;

import com.tanguylegoff.templateapp.api.models.ProfileDto;
import com.tanguylegoff.templateapp.config.mappers.GenericMapper;
import com.tanguylegoff.templateapp.dao.db.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Profile and ProfileDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProfileMapper extends GenericMapper<Profile, ProfileDto> {
}

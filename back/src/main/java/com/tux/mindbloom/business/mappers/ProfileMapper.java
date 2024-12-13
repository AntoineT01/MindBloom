package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.ProfileDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Profile and ProfileDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProfileMapper extends GenericMapper<Profile, ProfileDto> {
}

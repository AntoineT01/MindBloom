package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.SomethingDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Something;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Something and SomethingDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SomethingMapper extends GenericMapper<Something, SomethingDto> {
}

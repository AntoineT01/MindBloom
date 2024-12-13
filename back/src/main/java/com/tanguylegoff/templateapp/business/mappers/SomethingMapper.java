package com.tanguylegoff.templateapp.business.mappers;

import com.tanguylegoff.templateapp.api.models.SomethingDto;
import com.tanguylegoff.templateapp.config.mappers.GenericMapper;
import com.tanguylegoff.templateapp.dao.db.entities.Something;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Something and SomethingDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SomethingMapper extends GenericMapper<Something, SomethingDto> {
}

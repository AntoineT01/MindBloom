package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.CountryDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Country and CountryDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CountriesMapper extends GenericMapper<Country, CountryDto> {
}

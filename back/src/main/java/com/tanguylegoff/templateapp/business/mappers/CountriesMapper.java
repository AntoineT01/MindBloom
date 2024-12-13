package com.tanguylegoff.templateapp.business.mappers;

import com.tanguylegoff.templateapp.api.models.CountryDto;
import com.tanguylegoff.templateapp.config.mappers.GenericMapper;
import com.tanguylegoff.templateapp.dao.db.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Country and CountryDto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CountriesMapper extends GenericMapper<Country, CountryDto> {
}

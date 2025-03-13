package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.MediaDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Media;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre Media et MediaDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MediaMapper extends GenericMapper<Media, MediaDto> {
    // Les méthodes toEntity(...) et toDto(...) viennent de l'interface GenericMapper
}

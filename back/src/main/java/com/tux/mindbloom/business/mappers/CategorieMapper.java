package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.CategorieDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre Categorie et CategorieDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategorieMapper extends GenericMapper<Categorie, CategorieDto> {
    // Les méthodes toEntity(...) et toDto(...) héritées de GenericMapper
    // ou déclarées explicitement si vous n'utilisez pas GenericMapper.
}

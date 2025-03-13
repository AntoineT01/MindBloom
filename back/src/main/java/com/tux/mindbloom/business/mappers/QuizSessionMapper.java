package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.QuizSessionDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.QuizSession;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre QuizSession et QuizSessionDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuizSessionMapper extends GenericMapper<QuizSession, QuizSessionDto> {
    // Les méthodes toEntity(...) et toDto(...) sont héritées de GenericMapper.
    // Vous pouvez ajouter des conversions personnalisées si nécessaire.
}

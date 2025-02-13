package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.QuizStatisticsDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.QuizStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre QuizStatistics et QuizStatisticsDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuizStatisticsMapper extends GenericMapper<QuizStatistics, QuizStatisticsDto> {
    // Les méthodes toEntity(...) et toDto(...) peuvent provenir de GenericMapper.
    // Ajoutez-les explicitement si vous ne disposez pas de l'interface GenericMapper.
}

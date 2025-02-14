package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.QuizCategoriesDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.QuizCategories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct pour convertir entre QuizCategories et QuizCategoriesDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuizCategoriesMapper extends GenericMapper<QuizCategories, QuizCategoriesDto> {

    @Override
    @Mapping(target = "quizId", source = "quizId")
    @Mapping(target = "categoryId", source = "categoryId")
    QuizCategoriesDto toDto(QuizCategories entity);

    @Override
    @Mapping(target = "quizId", source = "quizId")
    @Mapping(target = "categoryId", source = "categoryId")
    QuizCategories toEntity(QuizCategoriesDto dto);
}

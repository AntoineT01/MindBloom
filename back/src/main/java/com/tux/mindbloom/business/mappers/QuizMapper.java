package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.QuizDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuizMapper extends GenericMapper<Quiz, QuizDto> {
}

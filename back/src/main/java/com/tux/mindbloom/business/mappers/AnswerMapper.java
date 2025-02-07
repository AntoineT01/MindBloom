package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.AnswerDto;
import com.tux.mindbloom.dao.db.entities.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

  AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

  @Mapping(source = "question.id", target = "questionId")
  AnswerDto toDto(Answer answer);

  // When converting DTO to entity, ignore the question field as it will be set in the service.
  @Mapping(target = "question", ignore = true)
  Answer toEntity(AnswerDto dto);
}

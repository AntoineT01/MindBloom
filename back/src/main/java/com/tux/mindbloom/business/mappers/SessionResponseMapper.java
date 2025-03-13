package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.SessionResponseDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.SessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre SessionResponse et SessionResponseDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SessionResponseMapper extends GenericMapper<SessionResponse, SessionResponseDto> {
    // Si vous n'avez pas GenericMapper, déclarez manuellement:
    // SessionResponse toEntity(SessionResponseDto dto);
    // SessionResponseDto toDto(SessionResponse entity);
    // List<SessionResponseDto> toDtos(List<SessionResponse> entities);
    // etc.
}

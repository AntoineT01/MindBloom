package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.ParticipantDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre Participant et ParticipantDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ParticipantMapper extends GenericMapper<Participant, ParticipantDto> {
    // Les méthodes toEntity(...) et toDto(...) héritées de GenericMapper.
    // Si vous n'utilisez pas GenericMapper, déclarez explicitement :
    // - Participant toEntity(ParticipantDto dto);
    // - ParticipantDto toDto(Participant entity);
    // - etc.
}

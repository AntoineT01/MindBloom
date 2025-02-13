package com.tux.mindbloom.business.mappers;

import com.tux.mindbloom.api.models.LeaderboardDto;
import com.tux.mindbloom.config.mappers.GenericMapper;
import com.tux.mindbloom.dao.db.entities.Leaderboard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper MapStruct permettant de convertir entre Leaderboard et LeaderboardDto.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LeaderboardMapper extends GenericMapper<Leaderboard, LeaderboardDto> {
    // Les méthodes toEntity(...) et toDto(...) viennent de GenericMapper.
    // Si vous n'utilisez pas GenericMapper, déclarez-les explicitement.
}

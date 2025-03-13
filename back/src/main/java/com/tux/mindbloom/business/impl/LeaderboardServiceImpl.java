package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.LeaderboardDto;
import com.tux.mindbloom.business.LeaderboardService;
import com.tux.mindbloom.business.mappers.LeaderboardMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.LeaderboardRepository;
import com.tux.mindbloom.dao.db.entities.Leaderboard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.LeaderboardService}.
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final LeaderboardMapper leaderboardMapper;

    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository,
                                  LeaderboardMapper leaderboardMapper) {
        this.leaderboardRepository = leaderboardRepository;
        this.leaderboardMapper = leaderboardMapper;
    }

    @Override
    public List<LeaderboardDto> findAll() {
        List<Leaderboard> allEntries = leaderboardRepository.findAll();
        return leaderboardMapper.toDtos(allEntries);
    }

    @Override
    public LeaderboardDto findById(Long id) {
        return leaderboardRepository.findById(id)
                .map(leaderboardMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(Leaderboard.class.getSimpleName(), id));
    }

    @Override
    public LeaderboardDto create(LeaderboardDto dto) {
        Leaderboard entity = leaderboardMapper.toEntity(dto);
        Leaderboard created = leaderboardRepository.save(entity);
        return leaderboardMapper.toDto(created);
    }

    @Override
    public LeaderboardDto updateById(Long id, LeaderboardDto dto) {
        Optional<Leaderboard> optional = leaderboardRepository.findById(id);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException(Leaderboard.class.getSimpleName(), id);
        }

        Leaderboard existing = optional.get();
        existing.setQuizSessionId(dto.getQuizSessionId());
        existing.setParticipantId(dto.getParticipantId());
        existing.setScore(dto.getScore());

        Leaderboard updated = leaderboardRepository.save(existing);
        return leaderboardMapper.toDto(updated);
    }

    @Override
    public Object deleteById(Long id) {
        if (!leaderboardRepository.existsById(id)) {
            throw new EntityNotFoundException(Leaderboard.class.getSimpleName(), id);
        }

        // Suppression définitive (ou soft-delete si besoin)
        leaderboardRepository.deleteById(id);

        return true;
    }
}

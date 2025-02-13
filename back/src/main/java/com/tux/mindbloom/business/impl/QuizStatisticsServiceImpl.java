package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.QuizStatisticsDto;
import com.tux.mindbloom.business.QuizStatisticsService;
import com.tux.mindbloom.business.mappers.QuizStatisticsMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.QuizStatisticsRepository;
import com.tux.mindbloom.dao.db.entities.QuizStatistics;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.QuizStatisticsService}.
 */
@Service
public class QuizStatisticsServiceImpl implements QuizStatisticsService {

    private final QuizStatisticsRepository quizStatisticsRepository;
    private final QuizStatisticsMapper quizStatisticsMapper;

    public QuizStatisticsServiceImpl(QuizStatisticsRepository quizStatisticsRepository,
                                     QuizStatisticsMapper quizStatisticsMapper) {
        this.quizStatisticsRepository = quizStatisticsRepository;
        this.quizStatisticsMapper = quizStatisticsMapper;
    }

    @Override
    public List<QuizStatisticsDto> findAll() {
        List<QuizStatistics> statsList = quizStatisticsRepository.findAll();
        return quizStatisticsMapper.toDtos(statsList);
    }

    @Override
    public QuizStatisticsDto findById(Long id) {
        return quizStatisticsRepository.findById(id)
                .map(quizStatisticsMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(QuizStatistics.class.getSimpleName(), id));
    }

    @Override
    public QuizStatisticsDto create(QuizStatisticsDto dto) {
        QuizStatistics stats = quizStatisticsMapper.toEntity(dto);
        QuizStatistics created = quizStatisticsRepository.save(stats);
        return quizStatisticsMapper.toDto(created);
    }

    @Override
    public QuizStatisticsDto updateById(Long id, QuizStatisticsDto dto) {
        Optional<QuizStatistics> optionalStats = quizStatisticsRepository.findById(id);
        if (!optionalStats.isPresent()) {
            throw new EntityNotFoundException(QuizStatistics.class.getSimpleName(), id);
        }

        QuizStatistics existingStats = optionalStats.get();
        existingStats.setQuizId(dto.getQuizId());
        existingStats.setTotalParticipants(dto.getTotalParticipants());
        existingStats.setAverageScore(dto.getAverageScore());
        existingStats.setAverageTimePerQuestion(dto.getAverageTimePerQuestion());

        QuizStatistics updatedStats = quizStatisticsRepository.save(existingStats);
        return quizStatisticsMapper.toDto(updatedStats);
    }

    @Override
    public Object deleteById(Long id) {
        // Vérifier si l'entité existe
        if (!quizStatisticsRepository.existsById(id)) {
            throw new EntityNotFoundException(QuizStatistics.class.getSimpleName(), id);
        }

        // Suppression définitive (ou logique si besoin)
        quizStatisticsRepository.deleteById(id);

        return true;
    }
}

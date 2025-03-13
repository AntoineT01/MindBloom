package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.QuizSessionDto;
import com.tux.mindbloom.business.QuizSessionService;
import com.tux.mindbloom.business.mappers.QuizSessionMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.QuizSessionRepository;
import com.tux.mindbloom.dao.db.entities.QuizSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.QuizSessionService}.
 */
@Service
public class QuizSessionServiceImpl implements QuizSessionService {

    private final QuizSessionRepository quizSessionRepository;
    private final QuizSessionMapper quizSessionMapper;

    public QuizSessionServiceImpl(QuizSessionRepository quizSessionRepository,
                                  QuizSessionMapper quizSessionMapper) {
        this.quizSessionRepository = quizSessionRepository;
        this.quizSessionMapper = quizSessionMapper;
    }

    @Override
    public List<QuizSessionDto> findAll() {
        List<QuizSession> sessions = quizSessionRepository.findAll();
        return quizSessionMapper.toDtos(sessions);
    }

    @Override
    public QuizSessionDto findById(Long id) {
        return quizSessionRepository.findById(id)
                .map(quizSessionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(QuizSession.class.getSimpleName(), id));
    }

    @Override
    public QuizSessionDto create(QuizSessionDto dto) {
        QuizSession session = quizSessionMapper.toEntity(dto);
        QuizSession created = quizSessionRepository.save(session);
        return quizSessionMapper.toDto(created);
    }

    @Override
    public QuizSessionDto updateById(Long id, QuizSessionDto dto) {
        Optional<QuizSession> optionalSession = quizSessionRepository.findById(id);
        if (!optionalSession.isPresent()) {
            throw new EntityNotFoundException(QuizSession.class.getSimpleName(), id);
        }

        QuizSession existingSession = optionalSession.get();
        existingSession.setQuizId(dto.getQuizId());
        existingSession.setSessionMode(dto.getSessionMode());
        existingSession.setStatus(dto.getStatus());
        existingSession.setStartTime(dto.getStartTime());
        existingSession.setEndTime(dto.getEndTime());
        existingSession.setSessionCode(dto.getSessionCode());

        QuizSession updated = quizSessionRepository.save(existingSession);
        return quizSessionMapper.toDto(updated);
    }

    @Override
    public Object deleteById(Long id) {
        // Vérifier si l'entité existe
        if (!quizSessionRepository.existsById(id)) {
            throw new EntityNotFoundException(QuizSession.class.getSimpleName(), id);
        }

        // Suppression directe (ou soft-delete si besoin)
        quizSessionRepository.deleteById(id);

        // Retour
        return true;
    }
}

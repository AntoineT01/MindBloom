package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.SessionResponseDto;
import com.tux.mindbloom.business.SessionResponseService;
import com.tux.mindbloom.business.mappers.SessionResponseMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.SessionResponseRepository;
import com.tux.mindbloom.dao.db.entities.SessionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.SessionResponseService}.
 */
@Service
public class SessionResponseServiceImpl implements SessionResponseService {

    private final SessionResponseRepository sessionResponseRepository;
    private final SessionResponseMapper sessionResponseMapper;

    public SessionResponseServiceImpl(SessionResponseRepository sessionResponseRepository,
                                      SessionResponseMapper sessionResponseMapper) {
        this.sessionResponseRepository = sessionResponseRepository;
        this.sessionResponseMapper = sessionResponseMapper;
    }

    @Override
    public List<SessionResponseDto> findAll() {
        List<SessionResponse> responses = sessionResponseRepository.findAll();
        return sessionResponseMapper.toDtos(responses);
    }

    @Override
    public SessionResponseDto findById(Long id) {
        return sessionResponseRepository.findById(id)
                .map(sessionResponseMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(SessionResponse.class.getSimpleName(), id));
    }

    @Override
    public SessionResponseDto create(SessionResponseDto dto) {
        SessionResponse entity = sessionResponseMapper.toEntity(dto);
        SessionResponse created = sessionResponseRepository.save(entity);
        return sessionResponseMapper.toDto(created);
    }

    @Override
    public SessionResponseDto updateById(Long id, SessionResponseDto dto) {
        Optional<SessionResponse> optional = sessionResponseRepository.findById(id);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException(SessionResponse.class.getSimpleName(), id);
        }

        SessionResponse existing = optional.get();
        existing.setSessionId(dto.getSessionId());
        existing.setParticipantId(dto.getParticipantId());
        existing.setQuestionId(dto.getQuestionId());
        existing.setAnswerId(dto.getAnswerId());
        existing.setResponseText(dto.getResponseText());
        existing.setSubmittedAt(dto.getSubmittedAt());
        existing.setIsCorrect(dto.getIsCorrect());

        SessionResponse updated = sessionResponseRepository.save(existing);
        return sessionResponseMapper.toDto(updated);
    }

    @Override
    public Object deleteById(Long id) {
        if (!sessionResponseRepository.existsById(id)) {
            throw new EntityNotFoundException(SessionResponse.class.getSimpleName(), id);
        }

        // Suppression définitive (ou soft-delete si besoin)
        sessionResponseRepository.deleteById(id);

        return true;
    }
}

package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.ParticipantDto;
import com.tux.mindbloom.business.ParticipantService;
import com.tux.mindbloom.business.mappers.ParticipantMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.ParticipantRepository;
import com.tux.mindbloom.dao.db.entities.Participant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.ParticipantService}.
 */
@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public ParticipantServiceImpl(ParticipantRepository participantRepository,
                                  ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    @Override
    public List<ParticipantDto> findAll() {
        List<Participant> participants = participantRepository.findAll();
        return participantMapper.toDtos(participants);
    }

    @Override
    public ParticipantDto findById(Long id) {
        return participantRepository.findById(id)
                .map(participantMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(Participant.class.getSimpleName(), id));
    }

    @Override
    public ParticipantDto create(ParticipantDto dto) {
        Participant participant = participantMapper.toEntity(dto);
        Participant created = participantRepository.save(participant);
        return participantMapper.toDto(created);
    }

    @Override
    public ParticipantDto updateById(Long id, ParticipantDto dto) {
        Optional<Participant> optionalParticipant = participantRepository.findById(id);
        if (!optionalParticipant.isPresent()) {
            throw new EntityNotFoundException(Participant.class.getSimpleName(), id);
        }

        Participant existing = optionalParticipant.get();
        existing.setSessionId(dto.getSessionId());
        existing.setAccountId(dto.getAccountId());
        existing.setNickname(dto.getNickname());
        existing.setJoinedAt(dto.getJoinedAt());

        Participant updated = participantRepository.save(existing);
        return participantMapper.toDto(updated);
    }

    @Override
    public Object deleteById(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new EntityNotFoundException(Participant.class.getSimpleName(), id);
        }

        // Suppression définitive (ou soft-delete si besoin)
        participantRepository.deleteById(id);

        return true;
    }
}

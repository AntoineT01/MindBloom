package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.ParticipantController;
import com.tux.mindbloom.api.models.ParticipantDto;
import com.tux.mindbloom.business.ParticipantService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link ParticipantController}.
 */
@Slf4j
@RestController
public class ParticipantControllerImpl extends AbstractRestController implements ParticipantController {

    private final ParticipantService participantService;

    public ParticipantControllerImpl(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Override
    public List<ParticipantDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.PARTICIPANTS);
        return logResponse(log, "GET", participantService.findAll());
    }

    @Override
    public ParticipantDto create(ParticipantDto dto) {
        log.info("Rest : POST - {}", Constants.Api.PARTICIPANTS);
        return logResponse(log, "POST", participantService.create(dto));
    }

    @Override
    public ParticipantDto updateById(Long id, ParticipantDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.PARTICIPANTS);
        return logResponse(log, "PUT", participantService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.PARTICIPANTS);
        return logResponse(log, "DELETE", participantService.deleteById(id));
    }
}

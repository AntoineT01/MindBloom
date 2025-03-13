package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.SessionResponseController;
import com.tux.mindbloom.api.models.SessionResponseDto;
import com.tux.mindbloom.business.SessionResponseService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link SessionResponseController}.
 */
@Slf4j
@RestController
public class SessionResponseControllerImpl extends AbstractRestController implements SessionResponseController {

    private final SessionResponseService sessionResponseService;

    public SessionResponseControllerImpl(SessionResponseService sessionResponseService) {
        this.sessionResponseService = sessionResponseService;
    }

    @Override
    public List<SessionResponseDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.SESSION_RESPONSES);
        return logResponse(log, "GET", sessionResponseService.findAll());
    }

    @Override
    public SessionResponseDto create(SessionResponseDto dto) {
        log.info("Rest : POST - {}", Constants.Api.SESSION_RESPONSES);
        return logResponse(log, "POST", sessionResponseService.create(dto));
    }

    @Override
    public SessionResponseDto updateById(Long id, SessionResponseDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.SESSION_RESPONSES);
        return logResponse(log, "PUT", sessionResponseService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.SESSION_RESPONSES);
        return logResponse(log, "DELETE", sessionResponseService.deleteById(id));
    }
}

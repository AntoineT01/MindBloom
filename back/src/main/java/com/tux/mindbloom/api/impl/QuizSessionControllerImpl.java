package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.QuizSessionController;
import com.tux.mindbloom.api.models.QuizSessionDto;
import com.tux.mindbloom.business.QuizSessionService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link QuizSessionController}.
 */
@Slf4j
@RestController
public class QuizSessionControllerImpl extends AbstractRestController implements QuizSessionController {

    private final QuizSessionService quizSessionService;

    public QuizSessionControllerImpl(QuizSessionService quizSessionService) {
        this.quizSessionService = quizSessionService;
    }

    @Override
    public List<QuizSessionDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.QUIZ_SESSION);
        return logResponse(log, "GET", quizSessionService.findAll());
    }

    @Override
    public QuizSessionDto create(QuizSessionDto dto) {
        log.info("Rest : POST - {}", Constants.Api.QUIZ_SESSION);
        return logResponse(log, "POST", quizSessionService.create(dto));
    }

    @Override
    public QuizSessionDto updateById(Long id, QuizSessionDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.QUIZ_SESSION);
        return logResponse(log, "PUT", quizSessionService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.QUIZ_SESSION);
        return logResponse(log, "DELETE", quizSessionService.deleteById(id));
    }
}

package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.QuizStatisticsController;
import com.tux.mindbloom.api.models.QuizStatisticsDto;
import com.tux.mindbloom.business.QuizStatisticsService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link QuizStatisticsController}.
 */
@Slf4j
@RestController
public class QuizStatisticsControllerImpl extends AbstractRestController implements QuizStatisticsController {

    private final QuizStatisticsService quizStatisticsService;

    public QuizStatisticsControllerImpl(QuizStatisticsService quizStatisticsService) {
        this.quizStatisticsService = quizStatisticsService;
    }

    @Override
    public List<QuizStatisticsDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.QUIZ_STATISTICS);
        return logResponse(log, "GET", quizStatisticsService.findAll());
    }

    @Override
    public QuizStatisticsDto create(QuizStatisticsDto dto) {
        log.info("Rest : POST - {}", Constants.Api.QUIZ_STATISTICS);
        return logResponse(log, "POST", quizStatisticsService.create(dto));
    }

    @Override
    public QuizStatisticsDto updateById(Long id, QuizStatisticsDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.QUIZ_STATISTICS);
        return logResponse(log, "PUT", quizStatisticsService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.QUIZ_STATISTICS);
        return logResponse(log, "DELETE", quizStatisticsService.deleteById(id));
    }
}

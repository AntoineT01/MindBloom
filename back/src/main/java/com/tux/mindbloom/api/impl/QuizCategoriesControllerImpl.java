package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.QuizCategoriesController;
import com.tux.mindbloom.api.models.QuizCategoriesDto;
import com.tux.mindbloom.business.QuizCategoriesService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link QuizCategoriesController}.
 */
@Slf4j
@RestController
public class QuizCategoriesControllerImpl extends AbstractRestController implements QuizCategoriesController {

    private final QuizCategoriesService quizCategoriesService;

    public QuizCategoriesControllerImpl(QuizCategoriesService quizCategoriesService) {
        this.quizCategoriesService = quizCategoriesService;
    }

    @Override
    public List<QuizCategoriesDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.QUIZ_CATEGORIES);
        return logResponse(log, "GET", quizCategoriesService.findAll());
    }

    @Override
    public QuizCategoriesDto create(QuizCategoriesDto dto) {
        log.info("Rest : POST - {}", Constants.Api.QUIZ_CATEGORIES);
        return logResponse(log, "POST", quizCategoriesService.create(dto));
    }

    @Override
    public QuizCategoriesDto findById(Long quizId, Long categoryId) {
        log.info("Rest : GET - {} / {}/{}", Constants.Api.QUIZ_CATEGORIES, quizId, categoryId);
        return logResponse(log, "GET", quizCategoriesService.findById(quizId, categoryId));
    }

    @Override
    public QuizCategoriesDto updateById(Long quizId, Long categoryId, QuizCategoriesDto dto) {
        log.info("Rest : PUT - {} / {}/{}", Constants.Api.QUIZ_CATEGORIES, quizId, categoryId);
        return logResponse(log, "PUT", quizCategoriesService.updateById(quizId, categoryId, dto));
    }

    @Override
    public Object deleteById(Long quizId, Long categoryId) {
        log.info("Rest : DELETE - {} / {}/{}", Constants.Api.QUIZ_CATEGORIES, quizId, categoryId);
        return logResponse(log, "DELETE", quizCategoriesService.deleteById(quizId, categoryId));
    }
}

package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.AnswerController;
import com.tux.mindbloom.api.models.AnswerDto;
import com.tux.mindbloom.business.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.ANSWER;

@Slf4j
@RestController
public class AnswerControllerImpl extends AbstractRestController implements AnswerController {

  private final AnswerService answerService;

  public AnswerControllerImpl(AnswerService answerService) {
    this.answerService = answerService;
  }

  @Override
  public List<AnswerDto> findAll() {
    log.info("Rest: GET - {}", ANSWER);
    return logResponse(log, "GET", answerService.findAll());
  }

  @Override
  public AnswerDto findById(Long id) {
    log.info("Rest: GET - {}/{}", ANSWER, id);
    return logResponse(log, "GET", answerService.findById(id));
  }

  @Override
  public AnswerDto create(AnswerDto dto) {
    log.info("Rest: POST - {}", ANSWER);
    logRequest(log, dto);
    return logResponse(log, "POST", answerService.create(dto));
  }

  @Override
  public AnswerDto updateById(Long id, AnswerDto dto) {
    log.info("Rest: PUT - {}/{}", ANSWER, id);
    logRequest(log, dto);
    return logResponse(log, "PUT", answerService.updateById(id, dto));
  }

  @Override
  public void deleteById(Long id) {
    log.info("Rest: DELETE - {}/{}", ANSWER, id);
    answerService.deleteById(id);
    logResponse(log, "DELETE");
  }

  @Override
  public List<AnswerDto> findByQuestionId(Long questionId) {
    log.info("Rest: GET - {}/question/{}", ANSWER, questionId);
    return logResponse(log, "GET", answerService.findByQuestionId(questionId));
  }
}

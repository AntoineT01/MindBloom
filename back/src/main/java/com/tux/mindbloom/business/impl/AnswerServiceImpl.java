package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.AnswerDto;
import com.tux.mindbloom.business.AnswerService;
import com.tux.mindbloom.business.mappers.AnswerMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.AnswerRepository;
import com.tux.mindbloom.dao.db.entities.Answer;
import com.tux.mindbloom.dao.db.entities.Question;
import com.tux.mindbloom.dao.db.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;
  private final AnswerMapper answerMapper;
  private final QuestionRepository questionRepository;

  public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper, QuestionRepository questionRepository) {
    this.answerRepository = answerRepository;
    this.answerMapper = answerMapper;
    this.questionRepository = questionRepository;
  }

  @Override
  public List<AnswerDto> findAll() {
    log.info("Service: GET all answers");
    return answerRepository.findAll()
      .stream()
      .map(answerMapper::toDto)
      .collect(Collectors.toList());
  }

  @Override
  public AnswerDto findById(Long id) {
    log.info("Service: GET answer by id: {}", id);
    Answer answer = answerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(Answer.class.getSimpleName(), id));
    return answerMapper.toDto(answer);
  }

  @Override
  public List<AnswerDto> findByQuestionId(Long questionId) {
    log.info("Service: GET answers for question id: {}", questionId);
    List<Answer> answers = answerRepository.findByQuestionId(questionId);
    if (answers == null || answers.isEmpty()) {
      throw new EntityNotFoundException("Answers", questionId);
    }
    return answers.stream()
      .map(answerMapper::toDto)
      .collect(Collectors.toList());
  }

  @Override
  public AnswerDto create(AnswerDto dto) {
    log.info("Service: POST create answer");
    Answer answer = answerMapper.toEntity(dto);
    answer.setCreatedAt(LocalDateTime.now());

    // Retrieve the Question entity from the database
    Question question = questionRepository.findById(dto.getQuestionId())
      .orElseThrow(() -> new EntityNotFoundException("Question", dto.getQuestionId()));
    answer.setQuestion(question);

    Answer created = answerRepository.save(answer);
    return answerMapper.toDto(created);
  }

  @Override
  public AnswerDto updateById(Long id, AnswerDto dto) {
    log.info("Service: PUT update answer with id: {}", id);
    Answer updated = answerRepository.findById(id)
      .map(existing -> {
        if (!existing.getQuestion().getId().equals(dto.getQuestionId())) {
          Question question = questionRepository.findById(dto.getQuestionId())
            .orElseThrow(() -> new EntityNotFoundException("Question", dto.getQuestionId()));
          existing.setQuestion(question);
        }
        existing.setContent(dto.getContent());
        existing.setType(dto.getType());
        existing.setIsCorrect(dto.getIsCorrect());
        existing.setAnswerOrder(dto.getAnswerOrder());
        existing.setUpdatedAt(LocalDateTime.now());
        return existing;
      })
      .orElseThrow(() -> new EntityNotFoundException("Answer", id));
    Answer saved = answerRepository.save(updated);
    return answerMapper.toDto(saved);
  }

  @Override
  public void deleteById(Long id) {
    log.info("Service: DELETE answer with id: {}", id);
    Answer answer = answerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Answer", id));
    answerRepository.delete(answer);
  }
}

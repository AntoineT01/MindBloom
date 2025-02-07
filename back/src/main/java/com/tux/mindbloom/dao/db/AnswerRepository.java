package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DB Access for answers.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  /**
   * Finds all answers associated with a given question id.
   *
   * @param questionId the id of the question
   * @return a list of answers for that question
   */
  List<Answer> findByQuestionId(Long questionId);
}

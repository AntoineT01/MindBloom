package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DB Access for questions
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  /**
   * Retrieves all questions associated with a given quiz.
   *
   * @param quizId the identifier of the quiz
   * @return a list of questions if found, else an empty list
   */
  @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId")
  List<Question> findByQuizId(Long quizId);
}

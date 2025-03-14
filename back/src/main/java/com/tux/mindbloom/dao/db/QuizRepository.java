package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

  Optional<Quiz> findById(Long id);

  List<Quiz> findByCreatorId(Long creatorId);
}
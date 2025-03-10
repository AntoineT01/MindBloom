package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Accès à la base de données pour l'entité QuizSession.
 */
@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
    Optional<QuizSession> findBySessionCode(String sessionCode);

}


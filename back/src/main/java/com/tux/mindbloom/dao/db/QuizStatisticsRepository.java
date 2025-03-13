package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.QuizStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité QuizStatistics.
 */
@Repository
public interface QuizStatisticsRepository extends JpaRepository<QuizStatistics, Long> {
    // Vous pouvez définir ici des méthodes de requête personnalisées si nécessaire.
}

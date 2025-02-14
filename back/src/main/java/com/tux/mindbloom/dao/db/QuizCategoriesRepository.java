package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.QuizCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité QuizCategories.
 */
@Repository
public interface QuizCategoriesRepository extends JpaRepository<QuizCategories, QuizCategories.QuizCategoriesId> {
    // Vous pouvez définir des méthodes de requête personnalisées si nécessaire
}

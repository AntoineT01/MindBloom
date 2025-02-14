package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Représente la liaison entre un quiz et une catégorie.
 * Clé primaire composite : (quiz_id, category_id).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(QuizCategories.QuizCategoriesId.class)
@Table(name = "quiz_categories")
public class QuizCategories {

    /**
     * Clé primaire composite (partie 1) : l'ID du quiz
     */
    @Id
    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    /**
     * Clé primaire composite (partie 2) : l'ID de la catégorie
     */
    @Id
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * Classe interne représentant la clé composite de QuizCategories.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizCategoriesId implements Serializable {
        private Long quizId;
        private Long categoryId;
    }
}

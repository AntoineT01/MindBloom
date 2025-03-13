package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Représentation des statistiques liées à un quiz.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quiz_statistics")
public class QuizStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'identifiant du quiz associé aux statistiques.
     */
    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    /**
     * Le nombre total de participants ayant passé ce quiz.
     */
    @Column(name = "total_participants", nullable = false)
    private Integer totalParticipants;

    /**
     * Le score moyen obtenu au quiz (5,2 => max 999.99).
     */
    @Column(name = "average_score", precision = 5, scale = 2)
    private BigDecimal averageScore;

    /**
     * Le temps moyen passé par question (5,2 => max 999.99).
     */
    @Column(name = "average_time_per_question", precision = 5, scale = 2)
    private BigDecimal averageTimePerQuestion;
}

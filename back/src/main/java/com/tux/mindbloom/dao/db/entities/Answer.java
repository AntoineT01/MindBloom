package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Entity representing an answer.
 */
@Entity
@Table(name = "answer")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The question to which this answer belongs.
   */
  @ManyToOne
  @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
  private Question question;

  @Column(name = "content", columnDefinition = "text", nullable = false)
  private String content;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "is_correct", nullable = false)
  private Boolean isCorrect;

  @Column(name = "answer_order", nullable = false)
  private Integer answerOrder;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}

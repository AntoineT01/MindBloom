package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Représentation d'une question dans la base de données
 */
@Entity
@Table(name = "question")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Question {

  /**
   * Identifiant auto-incrémenté
   */
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * Le quiz auquel appartient la question.
   * On suppose l'existence d'une entité Quiz.
   */
  @ManyToOne
  @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
  private Quiz quiz;

  /**
   * Le contenu de la question.
   */
  @Column(name = "content", columnDefinition = "text", nullable = false)
  private String content;

  /**
   * Le type de la question (par exemple, 'multiple_choice', 'true_false', 'open')
   */
  @Column(name = "type", nullable = false)
  private String type;

  /**
   * Nombre de points attribués pour une réponse correcte.
   */
  @Column(name = "points", nullable = false)
  private Integer points;

  /**
   * Ordre d'affichage de la question dans le quiz.
   */
  @Column(name = "question_order", nullable = false)
  private Integer questionOrder;

  /**
   * Indique si la question est obligatoire.
   */
  @Column(name = "is_required", nullable = false)
  private Boolean isRequired;

  /**
   * Indique si la question est active.
   */
  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  /**
   * Temps d'affichage de la question en secondes.
   */
  @Column(name = "display_time", nullable = false)
  private Integer displayTime;

  /**
   * Temps minimum autorisé pour répondre à la question (optionnel).
   */
  @Column(name = "time_min")
  private Integer timeMin;

  /**
   * Temps maximum autorisé pour répondre à la question (optionnel).
   */
  @Column(name = "time_max")
  private Integer timeMax;

  /**
   * Timestamp de création de la question.
   */
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**
   * Timestamp de la dernière mise à jour de la question.
   */
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * Indique si la question a été importée.
   */
  @Column(name = "imported", nullable = false)
  private Boolean imported;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (!thisEffectiveClass.equals(oEffectiveClass)) return false;
    Question that = (Question) o;
    return Objects.equals(getId(), that.getId()) &&
           Objects.equals(getQuiz(), that.getQuiz()) &&
           Objects.equals(getContent(), that.getContent()) &&
           Objects.equals(getType(), that.getType()) &&
           Objects.equals(getPoints(), that.getPoints()) &&
           Objects.equals(getQuestionOrder(), that.getQuestionOrder()) &&
           Objects.equals(getIsRequired(), that.getIsRequired()) &&
           Objects.equals(getIsActive(), that.getIsActive()) &&
           Objects.equals(getDisplayTime(), that.getDisplayTime()) &&
           Objects.equals(getTimeMin(), that.getTimeMin()) &&
           Objects.equals(getTimeMax(), that.getTimeMax()) &&
           Objects.equals(getCreatedAt(), that.getCreatedAt()) &&
           Objects.equals(getUpdatedAt(), that.getUpdatedAt()) &&
           Objects.equals(getImported(), that.getImported());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}

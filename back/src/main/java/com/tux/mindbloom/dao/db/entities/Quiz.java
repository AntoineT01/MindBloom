package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
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
 * Hibernate entity representing a Quiz.
 */
@Entity
@Table(
  name = "quiz",
  uniqueConstraints = {
    @UniqueConstraint(name = "uq_quiz_share_code", columnNames = "share_code")
  },
  indexes = {
    @Index(name = "idx_quiz_share_code", columnList = "share_code")
  }
)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

  /**
   * Auto-increment identifier.
   */
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The title of the quiz, must be set.
   */
  @Column(name = "title", nullable = false)
  private String title;

  /**
   * The description of the quiz, must be set.
   */
  @Column(name = "description", nullable = false)
  private String description;

  /**
   * The account that created the quiz.
   * Assumes that Account is another entity mapped to the "account" table.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "creator_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_account"))
  private Account creator;

  /**
   * Indicates whether the quiz is public.
   */
  @Column(name = "is_public", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean isPublic = false;

  /**
   * Indicates whether the quiz shows answers.
   */
  @Column(name = "show_answers", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean showAnswers = false;

  /**
   * Indicates whether the quiz shows the final score.
   */
  @Column(name = "show_final_score", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean showFinalScore = true;

  /**
   * The maximum amount of time in seconds to complete each question.
   */
  @Column(name = "time_limit", nullable = false, columnDefinition = "INT DEFAULT 0")
  private Integer timeLimit = 0;

  /**
   * The timestamp when the quiz was created.
   */
  @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  /**
   * The timestamp when the quiz was last updated.
   */
  @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;

  /**
   * The status of the quiz.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 10, columnDefinition = "ENUM('active', 'inactive', 'deleted') DEFAULT 'active'")
  private Status status = Status.ACTIVE;

  /**
   * The unique share code for the quiz.
   */
  @Column(name = "share_code", nullable = false, unique = true)
  private String shareCode;

  /**
   * Enumeration representing the possible statuses of a quiz.
   */
  public enum Status {
    ACTIVE,
    INACTIVE,
    DELETED
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;

    // Handle Hibernate proxy objects
    if (this instanceof HibernateProxy) {
      if (!(o instanceof HibernateProxy)) return false;
      Quiz other = (Quiz) ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation();
      return Objects.equals(id, other.id) &&
        Objects.equals(shareCode, other.shareCode);
    } else {
      if (o instanceof HibernateProxy) {
        Quiz other = (Quiz) ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation();
        return Objects.equals(id, other.id) &&
          Objects.equals(shareCode, other.shareCode);
      }
      if (getClass() != o.getClass()) return false;
      Quiz other = (Quiz) o;
      return Objects.equals(id, other.id) &&
        Objects.equals(shareCode, other.shareCode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {
    return Objects.hash(id, shareCode);
  }

  /**
   * Automatically sets createdAt and updatedAt before persisting.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
  }

  /**
   * Automatically updates updatedAt before updating.
   */
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}

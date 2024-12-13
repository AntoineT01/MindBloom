package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Representation of something in the database
 */
// Hibernate
@Entity
@Table(name = "something")
// Lombok
@SuperBuilder
@Getter
@Setter
// Requis par hibernate
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Something extends AuditModel {

  /**
   * Autoincrement identifier
   */
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * Something's label, must be set and unique
   */
  @Column(name = "label", unique = true, nullable = false)
  private String label;

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (this.getClass() != o.getClass()) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy objHibernateProxy ? objHibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy thisHibernateProxy ? thisHibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Something something = (Something) o;
    return Objects.equals(getId(), something.getId()) &&
            Objects.equals(getLabel(), something.getLabel());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy thisHibernateProxy ? thisHibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}

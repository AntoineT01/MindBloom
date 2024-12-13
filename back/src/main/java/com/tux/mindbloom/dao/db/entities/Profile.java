package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Representation of a profile in the database
 */
// Hibernate
@Entity
@Table(name = "profile")
// Lombok
@Builder
@Getter
@Setter
// Requis par hibernate
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

  /**
   * Autoincrement identifier
   */
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The profile's label, must be set and unique
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
    Profile profile = (Profile) o;
    return Objects.equals(getId(), profile.getId()) &&
            Objects.equals(getLabel(), profile.getLabel());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy thisHibernateProxy ? thisHibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}

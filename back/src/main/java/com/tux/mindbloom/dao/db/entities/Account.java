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

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Representation of an account in the database
 */
// Hibernate
@Entity
@Table(name = "account")
// Lombok
@Builder
@Getter
@Setter
// Requis par hibernate
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Account {

  /**
   * Autoincrement identifier
   */
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The profile account, must be set
   */
  @ManyToOne
  @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
  private Profile profile;

  /**
   * The account's firstname.
   * This is nullable in case we "delete" the account.
   */
  @Column(name = "firstname", length = 50)
  private String firstname;

  /**
   * The account's lastname.
   * This is nullable in case we "delete" the account.
   */
  @Column(name = "lastname", length = 50)
  private String lastname;

  /**
   * The account's password.
   * This is nullable in case we "delete" the account.
   */
  @Column(name = "password")
  private String password;

  /**
   * The account's mail.
   * This is the unique identifier for login.
   */
  @Column(name = "mail", length = 50, unique = true, nullable = false)
  private String mail;

  /**
   * The provider of the OAuth service (e.g., google, microsoft, facebook)
   */
  @Column(name = "oauth_provider", length = 50)
  private String oauthProvider;

  /**
   * The unique identifier for the account from the OAuth provider.
   */
  @Column(name = "oauth_id")
  private String oauthId;

  /**
   * IETF Tag of the selected locale for mails (fr, en)
   */
  @Column(name = "locale", length = 10)
  private String locale;

  /**
   * Active status for this account.
   * Should be false if mail has not been checked.
   * Should be false if account was deleted
   */
  @Column(name = "active", nullable = false)
  private Boolean active;

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
    Account account = (Account) o;
    return Objects.equals(getId(), account.getId()) &&
      Objects.equals(getFirstname(), account.getFirstname()) &&
      Objects.equals(getLastname(), account.getLastname()) &&
      Objects.equals(getProfile(), account.getProfile()) &&
      Objects.equals(getPassword(), account.getPassword()) &&
      Objects.equals(getMail(), account.getMail()) &&
      Objects.equals(getOauthProvider(), account.getOauthProvider()) &&
      Objects.equals(getOauthId(), account.getOauthId()) &&
      Objects.equals(getLocale(), account.getLocale()) &&
      Objects.equals(getActive(), account.getActive());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy thisHibernateProxy ? thisHibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}

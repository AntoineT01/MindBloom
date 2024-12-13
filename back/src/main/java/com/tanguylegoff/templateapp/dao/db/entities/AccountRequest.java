package com.tanguylegoff.templateapp.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Representation of an account request in the database
 */
// Hibernate
@Entity
@Table(name = "account_request")
// Lombok
@Builder
@Getter
@Setter
// Requis par hibernate
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

  /**
   * Autoincrement identifier
   */
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * An account request token, used to find the account again.
   * Must be set.
   */
  @Column(name = "token", length = 50, nullable = false)
  private String token;

  /**
   * The account to handle.
   * Must be set
   */
  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
  private Account account;

  /**
   * Expiration date for the request (will last somewhere up to 5-10 minutes)
   * Must be set
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expiration_date", nullable = false)
  private Date expirationDate;

  /**
   * The type of request (reset password, etc.)
   * Must be set
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "request_type", nullable = false)
  private RequestType requestType;

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
    AccountRequest accountRequest = (AccountRequest) o;
    return Objects.equals(getId(), accountRequest.getId()) &&
            Objects.equals(getToken(), accountRequest.getToken()) &&
            Objects.equals(getAccount(), accountRequest.getAccount()) &&
            Objects.equals(getRequestType(), accountRequest.getRequestType()) &&
            Objects.equals(getExpirationDate(), accountRequest.getExpirationDate());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy thisHibernateProxy ? thisHibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}

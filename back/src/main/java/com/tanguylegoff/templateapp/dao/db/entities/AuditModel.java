package com.tanguylegoff.templateapp.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditModel {

  /**
   * Account id of the account that has created the data.
   * Set during creation only
   */
  @Column(name = "created_by", nullable = false, updatable = false)
  @CreatedBy
  @EqualsAndHashCode.Exclude
  private Long createdBy;

  /**
   * Date of creation of this data.
   * Set during creation only
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_on", nullable = false, updatable = false)
  @CreatedDate
  @EqualsAndHashCode.Exclude
  private Date createdOn;

  /**
   * Account id of the account that has created the data.
   * Set during creation, then after each update
   */
  @Column(name = "updated_by", nullable = false)
  @LastModifiedBy
  @EqualsAndHashCode.Exclude
  private Long updatedBy;

  /**
   * Date of update of this data.
   * Set during creation, then after each update
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_on", nullable = false)
  @LastModifiedDate
  @EqualsAndHashCode.Exclude
  private Date updatedOn;

}

package com.futurebizops.kpi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEnabledEntity {

    @CreatedDate
    @Column(name = "crte_ts",  nullable = false, updatable = false)
    private Instant createdDate;

    @Column(name = "crte_user_id")
    private String createdUserId;

    @LastModifiedDate
    @Column(name = "lst_updt_ts")
    private Instant updatedDate;

    @Column(name = "lst_updt_user_id")
    private String updatedUserId;
}

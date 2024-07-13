package com.java.spring.batch.statement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customer_logging")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLogging {

  @Id
  @Column(name = "logging_id")
  private int id;

  @Column(name = "email")
  private String email;

  @Column(name = "target_loc")
  private String targetLocation;

  @Column(name = "generated_at")
  private LocalDateTime generatedAt;

  @Column(name = "success")
  private boolean success;
}

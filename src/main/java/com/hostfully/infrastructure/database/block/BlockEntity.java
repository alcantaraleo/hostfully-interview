package com.hostfully.infrastructure.database.block;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "BLOCK")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BlockEntity {

  @Id
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalDate endDate;

  @Column(nullable = false)
  private UUID propertyId;

  @Column(nullable = false)
  private LocalDateTime createdAt;


}

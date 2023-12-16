package com.hostfully.infrastructure.database.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "PROPERTY")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PropertyEntity {

  @Id
  @Column(nullable = false)
  private UUID id;

  private String alias;

  private LocalDateTime createdAt;

}

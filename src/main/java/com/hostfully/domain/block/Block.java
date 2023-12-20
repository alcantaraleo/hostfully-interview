package com.hostfully.domain.block;

import com.hostfully.domain.property.Property;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@RequiredArgsConstructor(onConstructor_ = {@Builder})
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@With(AccessLevel.PRIVATE)
@Builder
public class Block {

  @ToString.Include
  @EqualsAndHashCode.Include
  private final UUID id;

  @ToString.Include
  private final LocalDate startDate;

  @ToString.Include
  private final LocalDate endDate;

  private final LocalDateTime createdAt;

  @ToString.Include
  private final Property property;

}

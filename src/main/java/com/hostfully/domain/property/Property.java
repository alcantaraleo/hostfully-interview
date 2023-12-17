package com.hostfully.domain.property;

import com.hostfully.infrastructure.configuration.annotations.Default;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;


@RequiredArgsConstructor(onConstructor_={@Default})
@With
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Property {

  @EqualsAndHashCode.Include
  @ToString.Include
  private final UUID id;
  private final String alias;
  private final LocalDateTime createdAt;

}

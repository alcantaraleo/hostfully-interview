package com.hostfully.application.block;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_={@Builder} )
@Getter
@Builder
public class BlockDTO {

  private final UUID id;

  @NotNull(message = "Block start date must be supplied")
  private final LocalDate startDate;

  @NotNull(message = "Block end date must be supplied")
  private final LocalDate endDate;

  @NotNull(message = "Property identifier must be supplied")
  private final UUID propertyId;
}

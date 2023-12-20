package com.hostfully.application.guest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class GuestDTO {

  @NotNull(message = "Guest's first name needs to be supplied")
  @Size(min = 1, message = "Guest's first name cannot be empty")
  private final String firstName;

  @NotNull(message = "Guest's last name needs to be supplied")
  @Size(min = 1, message = "Guest's last name cannot be empty")
  private final String lastName;

  private final LocalDate dateOfBirth;

}

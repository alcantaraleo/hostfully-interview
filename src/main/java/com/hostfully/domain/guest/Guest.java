package com.hostfully.domain.guest;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.With;

@With(AccessLevel.PRIVATE)
public record Guest(@NonNull String firstName, @NonNull String lastName, @NonNull LocalDate dateOfBirth) {

  public Guest updateDetails(@NonNull Guest proposedGuest) {

    var updatedGuest = this;

    if (!proposedGuest.firstName.isEmpty()) {
      updatedGuest = updatedGuest.withFirstName(proposedGuest.firstName);
    }

    if (!proposedGuest.lastName.isEmpty()) {
      updatedGuest = updatedGuest.withLastName(proposedGuest.lastName);
    }

    updatedGuest = updatedGuest.withDateOfBirth(proposedGuest.dateOfBirth);

    return updatedGuest;

  }

}

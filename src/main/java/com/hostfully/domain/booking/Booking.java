package com.hostfully.domain.booking;

import com.hostfully.domain.guest.Guest;
import com.hostfully.domain.property.Property;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@RequiredArgsConstructor
@ToString
@Getter
@With(AccessLevel.PRIVATE)
@Builder
public class Booking {

  private final UUID id;

  private final LocalDate startDate;
  private final LocalDate endDate;

  private final Guest guest;

  private final LocalDateTime cancellationDate;

  @Builder.Default
  private final BookingStatus status = BookingStatus.SCHEDULED;

  private final Property property;

  public boolean isCancelled() {
    return BookingStatus.CANCELLED.equals(this.status);
  }

  public boolean isCompleted() { return BookingStatus.COMPLETED.equals(this.status); }

  public Booking updateDates(@NonNull LocalDate proposedStartDate,
      @NonNull LocalDate proposedEndDate) {
    checkProposedDatesAreValid(proposedStartDate, proposedEndDate);
    return this.withStartDate(proposedStartDate).withEndDate(proposedEndDate);
  }

  public Booking updateGuestDetails(@NonNull Guest proposedUpdatedGuest) {
    return this.withGuest(this.guest.updateDetails(proposedUpdatedGuest));
  }

  public Booking cancel() {
    if (this.isCompleted()) {
      throw new BookingInvalidStatusException("Can't cancel an already completed booking");
    }

    //in case this booking is already cancelled, just return it
    if (this.isCancelled()) {
      return this;
    }

    return this.withStatus(BookingStatus.CANCELLED).withCancellationDate(LocalDateTime.now());

  }
  public Booking complete() {
    if (this.isCancelled()) {
      throw new BookingInvalidStatusException("Can't complete an already cancelled booking");
    }

    return this.withStatus(BookingStatus.COMPLETED);

  }

  public Booking rebook(@NonNull LocalDate proposedStartDate, @NonNull LocalDate proposedEndDate) {

    if (!this.isCancelled()) {
      throw new BookingInvalidStatusException("Cannot rebook a non cancelled booking");
    }

    checkProposedDatesAreValid(proposedStartDate, proposedEndDate);

    return this.withStartDate(proposedStartDate).withEndDate(proposedEndDate)
        .withStatus(BookingStatus.SCHEDULED);

  }

  private void checkProposedDatesAreValid(LocalDate proposedStartDate, LocalDate proposedEndDate) {
    if (proposedEndDate.isBefore(proposedStartDate)) {
      throw new BookingDatesInvalid("Supplied booking dates are invalid. Please verify");
    }
  }


}

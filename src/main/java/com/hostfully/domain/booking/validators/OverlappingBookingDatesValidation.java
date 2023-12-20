package com.hostfully.domain.booking.validators;

import com.hostfully.domain.booking.Booking;
import java.util.List;
import java.util.function.BiPredicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OverlappingBookingDatesValidation implements BookingValidation {

  private static final BiPredicate<Booking, Booking> OVERLAPPING_DATES_PREDICATE = (proposedBooking, existingBooking) ->
      (proposedBooking.getStartDate().isBefore(existingBooking.getEndDate()) &&
          proposedBooking.getEndDate().isAfter(existingBooking.getStartDate()));

  @Override
  public boolean isValid(Booking proposedBooking, List<Booking> existingBookings) {
    log.info("Validating booking {} for overlapping dates with already existing bookings",
        proposedBooking);

    return existingBookings.stream().noneMatch(
        existingBooking -> OVERLAPPING_DATES_PREDICATE.test(proposedBooking, existingBooking));

  }
}

package com.hostfully.domain.booking.services;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.validators.BookingValidation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainBookingValidationService {

  private final List<BookingValidation> validations;

  public boolean validate(Booking proposedBooking, List<Booking> existingBookings) {
    log.info("Validating proposed booking {}", proposedBooking);

    return this.validations.stream()
        .map(validation -> validation.isValid(proposedBooking, existingBookings))
        .noneMatch(result -> result == false);

  }

}

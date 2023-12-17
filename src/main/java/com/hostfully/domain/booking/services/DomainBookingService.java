package com.hostfully.domain.booking.services;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.BookingStatus;
import com.hostfully.domain.booking.exceptions.InvalidBookingException;
import com.hostfully.domain.booking.usecases.CancelBooking;
import com.hostfully.domain.booking.usecases.RebookBooking;
import com.hostfully.domain.booking.usecases.RegisterBooking;
import com.hostfully.domain.booking.usecases.UpdateGuestDetails;
import com.hostfully.domain.property.PropertyNotFoundException;
import com.hostfully.domain.property.PropertyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainBookingService implements RegisterBooking, CancelBooking, UpdateGuestDetails,
    RebookBooking {

  public static final List<BookingStatus> ACTIVE_BOOKINGS = List.of(BookingStatus.SCHEDULED);

  private final BookingRepository bookingRepository;

  private final DomainBookingValidationService bookingValidationService;

  private final PropertyRepository propertyRepository;

  @Override
  public Booking registerBooking(final Booking booking) {

    final var propertyId = booking.getProperty().getId();
    final var optionalProperty = this.propertyRepository.findById(propertyId);

    if (optionalProperty.isEmpty()) {
      log.info("Supplied Property identifier {} could not be found.", propertyId);
      throw new PropertyNotFoundException(
          "Property with identifier " + propertyId + " could not be found");
    }

    final var existingBookings = this.bookingRepository.findByPropertyIdAndStatus(propertyId,
        ACTIVE_BOOKINGS);

    final var isNewBookingValid = this.bookingValidationService.validate(booking, existingBookings);

    if (!isNewBookingValid) {
      log.info("Supplied Booking is not valid. Please verify it's details");
      throw new InvalidBookingException("Supplied booking is not valid");
    }

    return this.bookingRepository.save(booking);

  }

  @Override
  public Booking updateGuestDetails(Booking booking) {
    return null;
  }

  @Override
  public Booking cancelBooking(Booking booking) {
    return null;
  }

  @Override
  public Booking rebookBooking(Booking booking) {
    return null;
  }
}

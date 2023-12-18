package com.hostfully.domain.booking.services;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.BookingStatus;
import com.hostfully.domain.booking.exceptions.BookingNotFoundException;
import com.hostfully.domain.booking.exceptions.ErrorStatus;
import com.hostfully.domain.booking.exceptions.InvalidBookingException;
import com.hostfully.domain.booking.usecases.CancelBooking;
import com.hostfully.domain.booking.usecases.ListBookings;
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
    RebookBooking, ListBookings {

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
      log.error("Supplied Booking is not valid. Please verify it's details");
      throw new InvalidBookingException("Supplied booking is not valid",
          ErrorStatus.BOOKING_DATES_UNAVAILABLE);
    }

    return this.bookingRepository.save(booking);

  }

  @Override
  public Booking updateGuestDetails(Booking toBeUpdatedBooking) {
    final var booking = this.findBookingOrThrow(toBeUpdatedBooking);

    final var updatedBooking = booking.updateGuestDetails(toBeUpdatedBooking.getGuest());
    return this.bookingRepository.update(updatedBooking);

  }

  @Override
  public Booking cancelBooking(Booking bookingToBeCancelled) {
    final var booking = findBookingOrThrow(bookingToBeCancelled);

    final var cancelledBooking = booking.cancel();
    return this.bookingRepository.update(cancelledBooking);

  }

  @Override
  public Booking rebookBooking(Booking toBeRebookedBooking) {
    final var booking = findBookingOrThrow(
        toBeRebookedBooking);

    final var existingBookings = this.bookingRepository.findByPropertyIdAndStatus(
        booking.getProperty().getId(), ACTIVE_BOOKINGS);
    existingBookings.remove(booking);
    final var expectedRebooking = booking.rebook(toBeRebookedBooking.getStartDate(),
        toBeRebookedBooking.getEndDate());

    final var isRebookingValid = this.bookingValidationService.validate(expectedRebooking,
        existingBookings);

    if (!isRebookingValid) {
      log.error("Supplied dates for rebooking {} are not valid.", toBeRebookedBooking.getId());
      throw new InvalidBookingException("Supplied booking is not valid",
          ErrorStatus.BOOKING_DATES_UNAVAILABLE);
    }

    return this.bookingRepository.update(expectedRebooking);
  }

  @Override
  public List<Booking> listBookings() {
    return this.bookingRepository.findAll();
  }

  private Booking findBookingOrThrow(Booking toBeRebookedBooking) {
    final var optionalBooking = this.bookingRepository.findById(toBeRebookedBooking.getId());
    if (optionalBooking.isEmpty()) {
      throw new BookingNotFoundException(
          "Could not find Booking with id " + toBeRebookedBooking.getId());
    }
    return optionalBooking.get();
  }
}

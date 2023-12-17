package com.hostfully.domain.booking.validators;

import com.hostfully.domain.booking.Booking;
import java.util.List;

public interface BookingValidation {

  boolean isValid(Booking proposedBooking, List<Booking> existingBookings);

}

package com.hostfully.domain.block.validators;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.booking.Booking;
import java.util.List;

public interface BlockValidation {

  boolean isValid(Booking proposedBooking, List<Block> existingBookings);
}

package com.hostfully.domain.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

  Optional<Booking> findById(UUID bookingId);

  Booking save(Booking booking);

  Booking update(Booking booking);

  List<Booking> findAll();

  List<Booking> findByPropertyId(UUID propertyId);

  List<Booking> findByPropertyIdAndStatus(UUID propertyId, List<BookingStatus> statuses);

}

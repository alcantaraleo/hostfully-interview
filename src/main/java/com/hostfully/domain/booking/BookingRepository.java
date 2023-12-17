package com.hostfully.domain.booking;

import java.util.List;
import java.util.UUID;

public interface BookingRepository {

  Booking save(Booking booking);

  Booking update(Booking booking);

  List<Booking> findAll();

  List<Booking> findByPropertyId(UUID propertyId);

}

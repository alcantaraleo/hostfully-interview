package com.hostfully.domain.booking;

import com.hostfully.domain.guest.GuestFixture;
import java.time.LocalDate;
import java.util.UUID;

public class BookingFixture {

  private BookingFixture() {}

  public static Booking createBooking() {

    return Booking.builder()
        .id(UUID.randomUUID())
        .startDate(LocalDate.of(2024, 1,10))
        .endDate(LocalDate.of(2024, 1, 17))
        .guest(GuestFixture.createGuest())
        .build();
  }

}

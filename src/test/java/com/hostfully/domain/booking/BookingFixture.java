package com.hostfully.domain.booking;

import com.hostfully.domain.guest.GuestFixture;
import com.hostfully.domain.property.Property;
import com.hostfully.domain.property.PropertyFixture;
import java.time.LocalDate;
import java.util.UUID;

public class BookingFixture {

  private BookingFixture() {
  }

  public static Booking createBooking() {

    return BookingFixture.createBooking(
        LocalDate.of(2024, 1, 10),
        LocalDate.of(2024, 1, 17))
        ;
  }

  public static Booking createBooking(LocalDate startDate, LocalDate endDate) {
    return Booking.builder()
        .id(UUID.randomUUID())
        .startDate(startDate)
        .endDate(endDate)
        .property(PropertyFixture.createProperty())
        .guest(GuestFixture.createGuest())
        .status(BookingStatus.SCHEDULED)
        .build();
  }

  public static Booking createBookingWithProperty(Property property) {
    return BookingFixture.createBookingWithProperty(property,
        LocalDate.of(2024, 1, 10),
        LocalDate.of(2024, 1, 17))
        ;
  }

  public static Booking createBookingWithProperty(Property property, LocalDate startDate,
      LocalDate endDate) {
    return Booking.builder()
        .id(UUID.randomUUID())
        .startDate(startDate)
        .endDate(endDate)
        .property(property)
        .guest(GuestFixture.createGuest())
        .status(BookingStatus.SCHEDULED)
        .build();
  }

}

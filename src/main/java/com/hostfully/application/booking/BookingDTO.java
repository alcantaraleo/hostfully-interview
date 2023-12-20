package com.hostfully.application.booking;

import com.hostfully.application.guest.GuestDTO;
import com.hostfully.domain.booking.BookingStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
@Getter
@Builder
public class BookingDTO {

  @With
  private final UUID id;

  @NotNull(message = "Booking start date must be supplied")
  private final LocalDate startDate;

  @NotNull(message = "Booking end date must be supplied")
  private final LocalDate endDate;

  private final BookingStatus status;

  @Valid
  private final GuestDTO guest;

  @NotNull(message = "Property Identifier must be supplied")
  private final UUID propertyId;

  public static BookingDTO createFromId(UUID bookingId) {
    return BookingDTO.builder().id(bookingId).build();
  }

  public static BookingDTO createFromGuest(GuestDTO guest) {
    return BookingDTO.builder().guest(guest).build();
  }

}

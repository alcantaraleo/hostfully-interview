package com.hostfully.domain.booking.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingFixture;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.exceptions.BookingNotFoundException;
import com.hostfully.domain.booking.services.DomainBookingService;
import com.hostfully.domain.booking.services.DomainBookingValidationService;
import com.hostfully.domain.guest.GuestFixture;
import com.hostfully.domain.property.PropertyRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tags(value = {@Tag("Unit"), @Tag("Booking"), @Tag("UpdateGuestDetails")})
class UpdateGuestDetailsTest {

  @Mock
  private PropertyRepository propertyRepository;

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private DomainBookingValidationService bookingValidationService;

  @Captor
  private ArgumentCaptor<Booking> argumentCaptor;

  @InjectMocks
  private DomainBookingService subject;

  @Test
  @DisplayName("""
      Given an existing scheduled booking,
      should update guest details successfully,
      when performing update operation   
      """)
  void givenExistingScheduledBooking_ShouldUpdateGuestDetailsSuccessfully_WhenPerformingUpdateOperation() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final var newGuest = GuestFixture.createGuest("Update Guest", "Details Test");
    final var updateGuestDetailsBooking = Booking.builder().id(booking.getId()).guest(newGuest)
        .build();

    when(this.bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));

    //act
    this.subject.updateGuestDetails(updateGuestDetailsBooking);
    verify(this.bookingRepository).update(argumentCaptor.capture());
    final var result = argumentCaptor.getValue();

    //assert
    assertThat(result).isNotNull();
    assertThat(result.getGuest().firstName()).isNotEqualTo(booking.getGuest().firstName());
    assertThat(result.getGuest().firstName()).isEqualTo(newGuest.firstName());
    assertThat(result.getGuest().lastName()).isNotEqualTo(booking.getGuest().lastName());
    assertThat(result.getGuest().lastName()).isEqualTo(newGuest.lastName());

  }

  @Test
  @DisplayName("""
      Given a non existing booking identifier,
      should throw exception,
      when performing update guest details     
      """)
  void givenNonExistingBookingIdentifier_ShouldThrow_WhenPerformingUpdateOperation() {
    //arrange
    final var booking = BookingFixture.createBooking();
    when(this.bookingRepository.findById(booking.getId())).thenReturn(Optional.empty());

    //act
    Assertions.assertThatThrownBy(() -> this.subject.updateGuestDetails(booking)).isInstanceOf(
        BookingNotFoundException.class);

  }

}

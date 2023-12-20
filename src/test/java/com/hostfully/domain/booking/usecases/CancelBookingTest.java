package com.hostfully.domain.booking.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingFixture;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.exceptions.BookingNotFoundException;
import com.hostfully.domain.booking.services.DomainBookingService;
import com.hostfully.domain.booking.services.DomainBookingValidationService;
import com.hostfully.domain.property.PropertyRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tags(value = {@Tag("Unit"), @Tag("Booking"), @Tag("Cancel")})
class CancelBookingTest {

  @Mock
  private PropertyRepository propertyRepository;

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private DomainBookingValidationService bookingValidationService;

  @InjectMocks
  private DomainBookingService subject;

  @Test
  @DisplayName("""
      Given an existing scheduled booking,
      should cancel successfully,
      when performing cancellation      
      """)
  void givenExistingScheduledBooking_ShouldCancelSuccessfully_WhenPerformingCancellation() {
    //arrange
    final var booking = BookingFixture.createBooking();
    when(this.bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
    when(this.bookingRepository.update(Mockito.any(Booking.class))).thenReturn(booking.cancel());

    //act
    final var result = this.subject.cancelBooking(booking);

    //assert
    assertThat(result).isNotNull();
    assertThat(result.isCancelled()).isTrue();
    assertThat(result.getCancellationDate()).isNotNull();

  }

  @Test
  @DisplayName("""
      Given a non existing booking identifier,
      should throw exception,
      when performing cancellation     
      """)
  void givenNonExistingBookingIdentifier_ShouldThrowException_WhenPerformingCancellation() {
    //arrange
    final var booking = BookingFixture.createBooking();
    when(this.bookingRepository.findById(booking.getId())).thenReturn(Optional.empty());

    //act
    Assertions.assertThatThrownBy(() -> this.subject.cancelBooking(booking)).isInstanceOf(
        BookingNotFoundException.class);

  }

  @Test
  @DisplayName("""
      Given an already cancelled booking,
      should cancel successfully,
      when performing cancellation again    
      """)
  void givenCancelledScheduledBooking_ShouldCancelSuccessfully_WhenPerformingCancellationAgain() {
    //arrange
    final var booking = BookingFixture.createBooking().cancel();
    when(this.bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
    when(this.bookingRepository.update(Mockito.any(Booking.class))).thenReturn(booking.cancel());

    //act
    final var result = this.subject.cancelBooking(booking);

    //assert
    assertThat(result).isNotNull();
    assertThat(result.isCancelled()).isTrue();
    assertThat(result.getCancellationDate()).isNotNull();

  }

}

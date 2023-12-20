package com.hostfully.domain.booking.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.block.services.DomainBlockService;
import com.hostfully.domain.block.services.DomainBlockValidationService;
import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingFixture;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.exceptions.BookingNotFoundException;
import com.hostfully.domain.booking.exceptions.ErrorStatus;
import com.hostfully.domain.booking.exceptions.InvalidBookingException;
import com.hostfully.domain.booking.exceptions.InvalidBookingStatusException;
import com.hostfully.domain.booking.services.DomainBookingService;
import com.hostfully.domain.booking.services.DomainBookingValidationService;
import com.hostfully.domain.property.PropertyRepository;
import java.util.Collections;
import java.util.List;
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
@Tags(value = {@Tag("Unit"), @Tag("Booking"), @Tag("Rebook")})
class RebookBookingTest {

  @Mock
  private PropertyRepository propertyRepository;

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private DomainBookingValidationService bookingValidationService;

  @Mock
  private DomainBlockService domainBlockService;

  @Mock
  private DomainBlockValidationService domainBlockValidationService;

  @Captor
  private ArgumentCaptor<Booking> argumentCaptor;

  @InjectMocks
  private DomainBookingService subject;

  @Test
  @DisplayName("""
      Given a cancelled booking,
      should rebook successfully,
      when there are no other bookings  
      """)
  void givenCancelledScheduledBooking_ShouldRebookSuccessfully_WhenThereAreNoOtherBookings() {
    //arrange
    final var cancelledBooking = BookingFixture.createBooking().cancel();
    final List<Booking> existingBookings = Collections.emptyList();
    final List<Block> existingBlocks = Collections.emptyList();
    when(this.bookingRepository.findById(cancelledBooking.getId())).thenReturn(
        Optional.of(cancelledBooking));
    when(this.bookingRepository.findByPropertyIdAndStatus(cancelledBooking.getProperty().getId(),
        DomainBookingService.ACTIVE_BOOKINGS)).thenReturn(existingBookings
    );
    when(this.domainBlockService.findBlocksByProperty(
        cancelledBooking.getProperty().getId())).thenReturn(existingBlocks);
    when(this.bookingValidationService.validate(cancelledBooking, existingBookings)).thenReturn(
        true);
    when(this.domainBlockValidationService.validate(cancelledBooking, existingBlocks)).thenReturn(
        true);

    //act
    this.subject.rebookBooking(cancelledBooking);

    //assert
    verify(this.bookingRepository).update(argumentCaptor.capture());
    final var result = argumentCaptor.getValue();
    assertThat(result).isNotNull();
    assertThat(result.isCancelled()).isFalse();
    assertThat(result.getCancellationDate()).isNull();

  }

  @Test
  @DisplayName("""
      Given a cancelled booking,
      should not rebook successfully,
      when there are other bookings overlapping  
      """)
  void givenCancelledScheduledBooking_ShouldNotRebookSuccessfully_WhenThereOtherBookings() {
    //arrange
    final var cancelledBooking = BookingFixture.createBooking().cancel();
    final List<Booking> existingBookings = Collections.emptyList();
    final List<Block> existingBlocks = Collections.emptyList();
    when(this.bookingRepository.findById(cancelledBooking.getId())).thenReturn(
        Optional.of(cancelledBooking));
    when(this.bookingRepository.findByPropertyIdAndStatus(cancelledBooking.getProperty().getId(),
        DomainBookingService.ACTIVE_BOOKINGS)).thenReturn(existingBookings
    );
    when(this.domainBlockService.findBlocksByProperty(
        cancelledBooking.getProperty().getId())).thenReturn(existingBlocks);
    when(this.bookingValidationService.validate(cancelledBooking, existingBookings)).thenReturn(
        false);

    //act
    Assertions.assertThatThrownBy(() -> this.subject.rebookBooking(cancelledBooking)).isInstanceOf(
        InvalidBookingException.class);


  }

  @Test
  @DisplayName("""
      Given a cancelled booking,
      should not rebook successfully,
      when there are blocks overlapping  
      """)
  void givenCancelledScheduledBooking_ShouldNotRebookSuccessfully_WhenThereBlocks() {
    //arrange
    final var cancelledBooking = BookingFixture.createBooking().cancel();
    final List<Booking> existingBookings = Collections.emptyList();
    final List<Block> existingBlocks = Collections.emptyList();
    when(this.bookingRepository.findById(cancelledBooking.getId())).thenReturn(
        Optional.of(cancelledBooking));
    when(this.bookingRepository.findByPropertyIdAndStatus(cancelledBooking.getProperty().getId(),
        DomainBookingService.ACTIVE_BOOKINGS)).thenReturn(existingBookings
    );
    when(this.domainBlockService.findBlocksByProperty(
        cancelledBooking.getProperty().getId())).thenReturn(existingBlocks);
    when(this.bookingValidationService.validate(cancelledBooking, existingBookings)).thenReturn(
        true);
    when(this.domainBlockValidationService.validate(cancelledBooking, existingBlocks)).thenReturn(
        false);

    //act
    Assertions.assertThatThrownBy(() -> this.subject.rebookBooking(cancelledBooking)).isInstanceOf(
        InvalidBookingException.class);


  }

  @Test
  @DisplayName("""
      Given a non existing booking identifier
      should throw exception,
      when performing rebooking 
      """)
  void givenNonExistingIdentifier_ShouldThrowException_WhenPerformingRebooking() {
    //arrange
    final var booking = BookingFixture.createBooking();
    when(this.bookingRepository.findById(booking.getId())).thenReturn(Optional.empty());

    //act
    assertThatThrownBy(() -> this.subject.rebookBooking(booking)).isInstanceOf(
        BookingNotFoundException.class);

  }

  @Test
  @DisplayName("""
      Given a not cancelled booking,
      should not rebook successfully        
      """)
  void givenNotCancelledScheduledBooking_ShouldNotRebookSuccessfully() {
    //arrange
    final var booking = BookingFixture.createBooking();

    when(this.bookingRepository.findById(booking.getId())).thenReturn(
        Optional.of(booking));

    //act
    assertThatThrownBy(() -> this.subject.rebookBooking(booking)).isInstanceOf(
        InvalidBookingStatusException.class).satisfies(ex -> {
      final var invalidBookingStatusException = (InvalidBookingStatusException) ex;
      assertThat(invalidBookingStatusException.getErrorStatus()
          .equals(ErrorStatus.CANNOT_REBOOK_NOT_CANCELLED_BOOKING));
    });

  }

}

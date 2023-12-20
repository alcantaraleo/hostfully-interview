package com.hostfully.domain.booking.usecases;

import static org.mockito.Mockito.when;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.block.services.DomainBlockService;
import com.hostfully.domain.block.services.DomainBlockValidationService;
import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingFixture;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.exceptions.InvalidBookingException;
import com.hostfully.domain.booking.services.DomainBookingService;
import com.hostfully.domain.booking.services.DomainBookingValidationService;
import com.hostfully.domain.property.PropertyNotFoundException;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tags(value = {@Tag("Unit"), @Tag("Booking"), @Tag("RegisterBooking")})
class RegisterBookingTest {

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

  @InjectMocks
  private DomainBookingService subject;


  @Test
  @DisplayName("""
      Given valid booking for an existing property,
      then it should be registered
      """)
  void givenValidBookingForExistingProperty_ThenShouldRegister() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final List<Booking> existingBookings = Collections.emptyList();
    final List<Block> existingBlocks = Collections.emptyList();
    when(this.propertyRepository.findById(booking.getProperty().getId())).thenReturn(
        Optional.of(booking.getProperty()));
    when(this.bookingRepository.findByPropertyIdAndStatus(booking.getProperty().getId(),
        DomainBookingService.ACTIVE_BOOKINGS)).thenReturn(existingBookings);
    when(this.domainBlockService.findBlocksByProperty(booking.getProperty().getId())).thenReturn(existingBlocks);
    when(this.bookingValidationService.validate(booking, existingBookings)).thenReturn(true);
    when(this.domainBlockValidationService.validate(booking, existingBlocks)).thenReturn(true);
    when(this.bookingRepository.save(booking)).thenReturn(booking);

    //act
    final var result = this.subject.registerBooking(booking);

    //assert
    Assertions.assertThat(result).isNotNull();
  }

  @Test
  @DisplayName("""
      Given valid booking for non existing property,
      then it should throw
      """)
  void givenValidBookingForNonExistingProperty_ThenShouldThrow() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final List<Booking> existingBookings = Collections.emptyList();
    when(this.propertyRepository.findById(booking.getProperty().getId())).thenReturn(
        Optional.empty());

    //act
    Assertions.assertThatThrownBy(() -> this.subject.registerBooking(booking)).isInstanceOf(
        PropertyNotFoundException.class);

    //assert

  }

  @Test
  @DisplayName("""
      Given booking for an existing property,
      when validations fails,
      then it should throw
      """)
  void givenBookingForExistingProperty_WhenValidationsFails_ThenShouldThrow() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final List<Booking> existingBookings = Collections.emptyList();
    when(this.propertyRepository.findById(booking.getProperty().getId())).thenReturn(
        Optional.of(booking.getProperty()));
    when(this.bookingRepository.findByPropertyIdAndStatus(booking.getProperty().getId(),
        DomainBookingService.ACTIVE_BOOKINGS)).thenReturn(existingBookings);
    when(this.bookingValidationService.validate(booking, existingBookings)).thenReturn(false);


    //act
    Assertions.assertThatThrownBy(() -> this.subject.registerBooking(booking)).isInstanceOf(
        InvalidBookingException.class);

  }

  @Test
  @DisplayName("""
      Given block for an existing property,
      when validations fails,
      then it should throw
      """)
  void givenBlockForExistingProperty_WhenValidationsFails_ThenShouldThrow() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final List<Booking> existingBookings = Collections.emptyList();
    final List<Block> existingBlocks = Collections.emptyList();
    when(this.propertyRepository.findById(booking.getProperty().getId())).thenReturn(
        Optional.of(booking.getProperty()));
    when(this.bookingRepository.findByPropertyIdAndStatus(booking.getProperty().getId(),
        DomainBookingService.ACTIVE_BOOKINGS)).thenReturn(existingBookings);
    when(this.domainBlockService.findBlocksByProperty(booking.getProperty().getId())).thenReturn(existingBlocks);
    when(this.bookingValidationService.validate(booking, existingBookings)).thenReturn(false);

    //act
    Assertions.assertThatThrownBy(() -> this.subject.registerBooking(booking)).isInstanceOf(
        InvalidBookingException.class);

  }


}

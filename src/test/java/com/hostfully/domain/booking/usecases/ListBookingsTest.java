package com.hostfully.domain.booking.usecases;

import static org.mockito.Mockito.when;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingFixture;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.services.DomainBookingService;
import com.hostfully.domain.booking.services.DomainBookingValidationService;
import com.hostfully.domain.property.PropertyRepository;
import java.time.LocalDate;
import java.util.List;
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
@Tags(value = {@Tag("Unit"), @Tag("Booking"), @Tag("List")})
class ListBookingsTest {

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
      Given existing Bookings,
      then they should be returned      
      """)
  void givenExistingBookings_ThenTheyShouldBeReturned() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final var secondBooking = BookingFixture.createBooking(LocalDate.now(),
        LocalDate.now().plusDays(2));
    final List<Booking> existingBookings = List.of(booking, secondBooking);
    when(this.bookingRepository.findAll()).thenReturn(existingBookings);

    //act
    final var result = this.subject.listBookings();

    //assert
    Assertions.assertThat(result).isNotNull().isNotEmpty().hasSize(2)
        .containsExactlyInAnyOrder(booking, secondBooking);
  }

}

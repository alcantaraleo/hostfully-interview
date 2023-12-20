package com.hostfully.domain.booking.validators;

import static com.hostfully.domain.booking.BookingFixture.createBooking;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingFixture;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Tags(value = {@Tag("Unit"), @Tag("Booking"), @Tag("Validation")})
public class OverlappingBookingDatesValidationTest {

  public static final List<Booking> EMPTY_BOOKING_LIST = Collections.emptyList();
  public static final Booking OVERLAPPING_START_DATE_BOOKING = BookingFixture.createBooking(
      LocalDate.of(2024, 1, 23), LocalDate.of(2024, 1, 30));
  public static final Booking OVERLAPPING_END_DATE_BOOKING = BookingFixture.createBooking(
      LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 23));
  public static final Booking OVERLAPPING_CONTAINED_BOOKING = BookingFixture.createBooking(
      LocalDate.of(2024, 1, 19), LocalDate.of(2024, 1, 23));

  public static final Booking OVERLAPPING_CONTAINING_BOOKING = BookingFixture.createBooking(
      LocalDate.of(2024, 1, 17), LocalDate.of(2024, 1, 25));

  public static final List<Booking> EXISTING_BOOKINGS = List.of(
      createBooking(LocalDate.of(2024, 1, 18), LocalDate.of(2024, 1, 24)),
      createBooking(LocalDate.of(2024, 1, 26), LocalDate.of(2024, 1, 29)),
      createBooking(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 28))
  );
  private final OverlappingBookingDatesValidation subject = new OverlappingBookingDatesValidation();

  @ParameterizedTest
  @DisplayName("""
      Given new proposed Booking {0},
      should check against list of bookings (if exist),
      then it should return {2}
      """)
  @MethodSource("createParameterizedBookingForTesting")
  void givenParametersForTest_ShouldAssessResultMatchesExpected(Booking newBooking, List<Booking> existingBookings, boolean expectedResult) {

    //act
    final var result = this.subject.isValid(newBooking, existingBookings);

    //assert
    Assertions.assertThat(result).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> createParameterizedBookingForTesting() {
    return Stream.of(
        Arguments.of(createBooking(), EMPTY_BOOKING_LIST, true),
        Arguments.of(createBooking(), EXISTING_BOOKINGS, true),
        Arguments.of(OVERLAPPING_START_DATE_BOOKING, EXISTING_BOOKINGS, false),
        Arguments.of(OVERLAPPING_END_DATE_BOOKING, EXISTING_BOOKINGS, false),
        Arguments.of(OVERLAPPING_CONTAINED_BOOKING, EXISTING_BOOKINGS, false),
        Arguments.of(OVERLAPPING_CONTAINING_BOOKING, EXISTING_BOOKINGS, false));
  }


}

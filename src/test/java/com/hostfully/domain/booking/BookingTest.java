package com.hostfully.domain.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hostfully.domain.guest.GuestFixture;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;


@Tags(value = {@Tag("Domain"), @Tag("Booking")})
class BookingTest {

  @Test
  @DisplayName("""
      Given a valid scheduled booking,
      should return false
      when checking if it is cancelled
      """)
  void givenScheduledBookingShouldReturnFalseWhenCheckingIsCancelled() {
    //arrange
    final var booking = BookingFixture.createBooking();

    //act
    final var result = booking.isCancelled();

    //assert
    assertThat(result).isFalse();
  }

  @Test
  @DisplayName("""
      Given a valid scheduled booking,
      should return true
      when cancelling and then checking if it was cancelled
      """)
  void givenValidBookingShouldReturnTrueWhenCancellingAndCheckingIsCancelled() {
    //arrange
    final var booking = BookingFixture.createBooking();

    //act
    final var cancelledBooking = booking.cancel();

    //assert
    assertThat(cancelledBooking.isCancelled()).isTrue();
  }

  @Test
  @DisplayName("""
      Given a valid completed booking,
      should throw error,
      when cancelling
      """)
  void givenCompletedBookingShouldThrowErrorWhenCancelling() {
    //arrange
    final var booking = BookingFixture.createBooking().complete();

    //act
    assertThatThrownBy(booking::cancel).isInstanceOf(BookingInvalidStatusException.class);
  }

  @Test
  @DisplayName("""
      Given a valid scheduled booking,
      should complete successfully,
      when completing the booking
      """)
  void givenValidScheduledBookingShouldCompleteSuccessfullyWhenCompleting() {
    //arrange
    final var booking = BookingFixture.createBooking();

    //act
    final var result = booking.complete();

    //arrange
    assertThat(result.isCompleted()).isTrue();

  }

  @Test
  @DisplayName("""
      Given a cancelled booking,
      should not complete successfully,
      when completing the booking
      """)
  void givenCancelledScheduledBookingShouldNotCompleteSuccessfullyWhenCompleting() {
    //arrange
    final var booking = BookingFixture.createBooking().cancel();

    //act && assert
    assertThatThrownBy(booking::complete).isInstanceOf(BookingInvalidStatusException.class);

  }


  @Test
  @DisplayName("""
      Given a valid scheduled booking,
      should update the booking successfully,
      when supplying valid proposed booking dates
      """)
  void givenScheduledBookingShouldUpdateDatesWhenValidProposedDatesAreSupplied() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final var proposedStartDate = LocalDate.of(2024, 2, 1);
    final var proposedEndDate = LocalDate.of(2024, 2, 6);

    //act
    final var result = booking.updateDates(proposedStartDate, proposedEndDate);

    //assert
    checkBookingDatesMatchWithProposedDates(proposedStartDate, proposedEndDate, result);
  }

  @Test
  @DisplayName("""
      Given a valid scheduled booking,
      should not update the booking successfully,
      when supplying invalid proposed booking dates
      """)
  void givenScheduledBookingShouldNotUpdateDatesWhenInvalidProposedDatesAreSupplied() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final var proposedStartDate = LocalDate.of(2024, 2, 10);
    final var proposedEndDate = LocalDate.of(2024, 2, 2);

    //act && assert
    Assertions.assertThatThrownBy(() -> booking.updateDates(proposedStartDate, proposedEndDate))
        .isInstanceOf(BookingDatesInvalid.class);

  }

  @Test
  @DisplayName("""
      Given a valid scheduled booking,
      should update the guest details,
      when supplying valid new guest
      """)
  void givenScheduledBookingShouldUpdateGuestDetailsWhenValidGuestIsSupplied() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final var proposedUpdatedGuest = GuestFixture.createAnotherGuest();

    //act
    final var result = booking.updateGuestDetails(proposedUpdatedGuest);

    //assert
    assertThat(result.getGuest()).satisfies(bookingGuest -> {
      assertThat(bookingGuest.firstName()).isEqualTo(proposedUpdatedGuest.firstName());
      assertThat(bookingGuest.lastName()).isEqualTo(proposedUpdatedGuest.lastName());
      assertThat(bookingGuest.dateOfBirth()).isEqualTo(proposedUpdatedGuest.dateOfBirth());
    });
  }

  @Test
  @DisplayName("""
      Given a cancelled booking,
      should rebook successfully,
      when rebooking
      """)
  void givenCancelledScheduledBookingShouldRebookSuccessfully() {
    //arrange
    final var booking = BookingFixture.createBooking().cancel();
    final var proposedStartDate = LocalDate.of(2024, 3, 1);
    final var proposedEndDate = LocalDate.of(2024, 3, 10);

    //act
    final var rebookedBooking = booking.rebook(proposedStartDate, proposedEndDate);

    checkBookingDatesMatchWithProposedDates(proposedStartDate, proposedEndDate, rebookedBooking);

  }

  @Test
  @DisplayName("""
      Given a scheduled booking,
      should not rebook successfully,
      when rebooking
      """)
  void givenScheduledScheduledBookingShouldNotRebookSuccessfully() {
    //arrange
    final var booking = BookingFixture.createBooking();
    final var proposedStartDate = LocalDate.of(2024, 3, 1);
    final var proposedEndDate = LocalDate.of(2024, 3, 10);

    //act
    assertThatThrownBy(() ->booking.rebook(proposedStartDate, proposedEndDate)).isInstanceOf(BookingInvalidStatusException.class);

  }

  @Test
  @DisplayName("""
      Given a cancelled booking,
      should not rebook successfully,
      when proposed dates are invalid
      """)
  void givenCancelledScheduledBookingShouldNotRebookSuccessfullyWhenProposedDatesInvalid() {
    //arrange
    final var booking = BookingFixture.createBooking().cancel();
    final var proposedStartDate = LocalDate.of(2024, 3, 10);
    final var proposedEndDate = LocalDate.of(2024, 3, 5);

    //act
    assertThatThrownBy(() ->booking.rebook(proposedStartDate, proposedEndDate)).isInstanceOf(BookingDatesInvalid.class);

  }

  private void checkBookingDatesMatchWithProposedDates(LocalDate proposedStartDate,
      LocalDate proposedEndDate,
      Booking newBooking) {
    assertThat(newBooking.getStartDate()).isEqualTo(proposedStartDate);
    assertThat(newBooking.getEndDate()).isEqualTo(proposedEndDate);
  }


}

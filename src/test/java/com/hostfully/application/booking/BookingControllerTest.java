package com.hostfully.application.booking;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static org.assertj.core.api.Assertions.assertThat;

import com.hostfully.AbstractIntegrationTest;
import com.hostfully.application.guest.GuestDTO;
import com.hostfully.domain.booking.BookingFixture;
import com.hostfully.domain.booking.BookingStatus;
import com.hostfully.domain.property.Property;
import com.hostfully.domain.property.PropertyFixture;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags(value = {@Tag("Integration"), @Tag("Property")})
class BookingControllerTest extends AbstractIntegrationTest {

  private Property testProperty;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.standaloneSetup(
        new BookingController(this.bookingModelDTOMapper, this.registerBooking, this.listBookings,
            this.cancelBooking, this.rebookBooking, this.updateGuestDetails));
    testProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty("Booking Test Property"));
  }

  @Test
  @DisplayName("""
      When performing a POST request to Register Booking,
      Should return newly created Booking
      """)
  void whenPerformingPostRequestToRegisterBooking_ShouldReturnBooking() {

    //arrange

    final var bookingDTO = BookingDTO.builder().propertyId(testProperty.getId())
        .startDate(LocalDate.of(2024, 1, 1))
        .endDate(LocalDate.of(2024, 1, 10)).guest(
            this.buildGuestDTO()).build();

    //act && assert

    final var registeredBooking = with().contentType(ContentType.JSON).body(bookingDTO).when()
        .post("/bookings")
        .then().statusCode(201).extract().body().jsonPath().getObject(".", BookingDTO.class);

    assertThat(registeredBooking).isNotNull();
    assertThat(registeredBooking.getId()).isNotNull();
    assertThat(registeredBooking.getStatus()).isEqualTo(BookingStatus.SCHEDULED);

  }

  @Test
  @DisplayName("""
      When performing a GET request to List Bookings,
      Should return all bookings
      """)
  void whenPerformingGetRequestToListBookings_ShouldReturnAllBookings() {

    //arrange
    final var scheduledBooking = this.registerBooking.registerBooking(
        BookingFixture.createBookingWithProperty(this.testProperty));
    final var cancelledBooking = this.cancelBooking.cancelBooking(
        this.registerBooking.registerBooking(
            BookingFixture.createBookingWithProperty(this.testProperty,
                scheduledBooking.getEndDate().plusDays(1),
                scheduledBooking.getEndDate().plusDays(5))));

    //act && assert

    final var bookings = with().contentType(ContentType.JSON).when()
        .get("/bookings")
        .then().statusCode(200).extract().body().jsonPath().getList(".", BookingDTO.class);

    assertThat(bookings).isNotNull().isNotEmpty().hasSize(2);
    final var bookingsIds = bookings.stream().map(BookingDTO::getId).toList();
    assertThat(bookingsIds).containsExactlyInAnyOrder(scheduledBooking.getId(),
        cancelledBooking.getId());

  }

  @Test
  @DisplayName("""
      When performing a PATCH request to Cancel Booking,
      Should cancel the booking
      """)
  void whenPerformingCancelBooking_ShouldCancel() {

    //arrange
    final var scheduledBooking = this.registerBooking.registerBooking(
        BookingFixture.createBookingWithProperty(this.testProperty));

    //act

    with().contentType(ContentType.JSON).pathParam("bookingId", scheduledBooking.getId()).when()
        .patch("/bookings/{bookingId}/cancel")
        .then().statusCode(200);

    //assert
    final var cancelledBooking = this.bookingRepository.findById(scheduledBooking.getId());

    Assertions.assertThat(cancelledBooking).isNotEmpty().hasValueSatisfying(cb -> {
      Assertions.assertThat(cb.isCancelled()).isTrue();
    });

  }

  @Test
  @DisplayName("""
      When performing a PATCH request to Update Guest Details,
      Should update guest details
      """)
  void whenUpdatingGuestDetails_ShouldUpdateSuccessfully() {

    //arrange

    final var scheduledBooking = this.registerBooking.registerBooking(
        BookingFixture.createBookingWithProperty(this.testProperty));

    final var updatedGuestDTO = GuestDTO.builder().firstName("New name").lastName("New Last name")
        .dateOfBirth(LocalDate.of(1990, 1, 1)).build();

    //act && assert

    final var updateBooking = with().contentType(ContentType.JSON)
        .pathParam("bookingId", scheduledBooking.getId()).body(updatedGuestDTO).when()
        .patch("/bookings/{bookingId}/guest")
        .then().statusCode(200).extract().body().jsonPath().getObject(".", BookingDTO.class);

    assertThat(updateBooking).isNotNull();
    assertThat(updateBooking.getId()).isNotNull();
    assertThat(updateBooking.getGuest()).isEqualTo(updatedGuestDTO);

  }

  @Test
  @DisplayName("""
      Given a cancelled booking,
      When performing a PATCH to Rebook,
      Should rebook
      """)
  void whenRebookingACancelledBooking_ShouldRebookAndActivate() {

    //arrange

    final var cancelledBooking = this.cancelBooking.cancelBooking(
        this.registerBooking.registerBooking(
            BookingFixture.createBookingWithProperty(this.testProperty)));
    final var rebookBookingDTO = BookingDTO.builder().propertyId(testProperty.getId())
        .startDate(LocalDate.of(2024, 6, 1))
        .endDate(LocalDate.of(2024, 6, 10)).guest(
            this.buildGuestDTO()).build();

    //act && assert

    final var rebookedBooking = with().contentType(ContentType.JSON)
        .pathParam("bookingId", cancelledBooking.getId()).body(rebookBookingDTO).when()
        .patch("/bookings/{bookingId}/rebook")
        .then().statusCode(200).extract().body().jsonPath().getObject(".", BookingDTO.class);

    assertThat(rebookedBooking).isNotNull();
    assertThat(rebookedBooking.getId()).isNotNull();
    assertThat(rebookedBooking.getStatus()).isEqualTo(BookingStatus.SCHEDULED);

  }

}

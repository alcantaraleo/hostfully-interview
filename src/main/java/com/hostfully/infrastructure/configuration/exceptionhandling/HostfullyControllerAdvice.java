package com.hostfully.infrastructure.configuration.exceptionhandling;

import com.hostfully.domain.booking.exceptions.BookingNotFoundException;
import com.hostfully.domain.booking.exceptions.InvalidBookingException;
import com.hostfully.domain.booking.exceptions.InvalidBookingStatusException;
import com.hostfully.domain.property.PropertyNotFoundException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class HostfullyControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(PropertyNotFoundException.class)
  protected ProblemDetail handlePropertyNotFound(
      PropertyNotFoundException ex) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
        ex.getMessage());
    problemDetail.setTitle("Property not found");
    problemDetail.setType(URI.create("https://www.hostfully.com/problems/property-not-found"));

    log.error("Supplied property identifier could not be found", ex);

    return problemDetail;
  }

  @ExceptionHandler(InvalidBookingStatusException.class)
  protected ProblemDetail handleInvalidBookingStatus(
      InvalidBookingStatusException ex) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,
        ex.getMessage());
    problemDetail.setTitle("Invalid Booking Status");
    problemDetail.setType(URI.create("https://www.hostfully.com/problems/invalid-booking-status"));
    problemDetail.setProperty(ex.getErrorStatus().name(), ex.getErrorStatus().toString());

    log.error("Booking is in an inconsistent state for this operation", ex);

    return problemDetail;
  }

  @ExceptionHandler(InvalidBookingException.class)
  protected ProblemDetail handleInvalidBooking(
      InvalidBookingException ex) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,
        ex.getMessage());
    problemDetail.setTitle("Invalid Booking");
    problemDetail.setType(URI.create("https://www.hostfully.com/problems/invalid-booking"));
    problemDetail.setProperty(ex.getErrorStatus().name(), ex.getErrorStatus().toString());

    log.error("Booking is invalid", ex);

    return problemDetail;
  }

  @ExceptionHandler(BookingNotFoundException.class)
  protected ProblemDetail handleBookingNotFound(
      BookingNotFoundException ex) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
        ex.getMessage());
    problemDetail.setTitle("Booking Not Found");
    problemDetail.setType(URI.create("https://www.hostfully.com/problems/booking-not-found"));

    return problemDetail;
  }

}

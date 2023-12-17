package com.hostfully.domain.booking.exceptions;

public class InvalidBookingDatesException extends RuntimeException {

  public InvalidBookingDatesException(String message) {
    super(message);
  }

}

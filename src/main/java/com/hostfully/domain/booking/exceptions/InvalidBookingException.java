package com.hostfully.domain.booking.exceptions;

public class InvalidBookingException extends RuntimeException {

  public InvalidBookingException(String message) {
    super(message);
  }

}

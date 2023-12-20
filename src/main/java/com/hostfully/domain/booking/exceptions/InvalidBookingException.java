package com.hostfully.domain.booking.exceptions;

import lombok.Getter;

@Getter
public class InvalidBookingException extends RuntimeException {

  private final ErrorStatus errorStatus;

  public InvalidBookingException(String message) {
    super(message);
    this.errorStatus = ErrorStatus.DETAILS_UNAVAILABLE;
  }

  public InvalidBookingException(String message, ErrorStatus errorStatus) {
    super(message);
    this.errorStatus = errorStatus;
  }

}

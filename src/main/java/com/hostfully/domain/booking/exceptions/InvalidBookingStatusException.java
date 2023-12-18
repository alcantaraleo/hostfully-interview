package com.hostfully.domain.booking.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvalidBookingStatusException extends RuntimeException{

  private final ErrorStatus errorStatus;

  public InvalidBookingStatusException(String message) {
    super(message);
    this.errorStatus = ErrorStatus.DETAILS_UNAVAILABLE;
  }

  public InvalidBookingStatusException(String message, ErrorStatus errorStatus) {
    super(message);
    this.errorStatus = errorStatus;
  }

}

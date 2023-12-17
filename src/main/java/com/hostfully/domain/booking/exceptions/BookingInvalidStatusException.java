package com.hostfully.domain.booking.exceptions;

public class BookingInvalidStatusException extends RuntimeException{

  public BookingInvalidStatusException(String message) {
    super(message);
  }

}

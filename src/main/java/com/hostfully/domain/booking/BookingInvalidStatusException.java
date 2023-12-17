package com.hostfully.domain.booking;

public class BookingInvalidStatusException extends RuntimeException{

  public BookingInvalidStatusException(String message) {
    super(message);
  }

}

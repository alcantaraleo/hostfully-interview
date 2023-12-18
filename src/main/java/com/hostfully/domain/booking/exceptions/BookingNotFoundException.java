package com.hostfully.domain.booking.exceptions;

public class BookingNotFoundException extends RuntimeException {

  public BookingNotFoundException(String message){
    super(message);
  }

}

package com.hostfully.domain.booking.exceptions;

public enum ErrorStatus {

  BOOKING_DATES_INVALID(1, "Start and End dates supplied are invalid"),
  BOOKING_DATES_UNAVAILABLE(2, "Start and End dates supplied are partially or fully unavailable"),
  BOOKING_CANT_BE_CANCELLED(3, "Booking can't be canceled'"),
  CANNOT_REBOOK_NOT_CANCELLED_BOOKING(4, "Booking can't be rebook as it isn't cancelled"),
  CANNOT_COMPLETE_CANCELLED_BOOKING(5, "Booking can't be completed as it is cancelled"),
  DETAILS_UNAVAILABLE(999, "Error details are unavailable");

  int errorCode;
  String errorMessage;

  ErrorStatus(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}

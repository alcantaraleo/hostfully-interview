package com.hostfully.domain.guest;

import java.time.LocalDate;

public class GuestFixture {

  private GuestFixture() {}

  public static Guest createGuest() {
    return new Guest("John", "Doe", LocalDate.of(1986, 4, 9));
  }

  public static Guest createAnotherGuest() {
    return new Guest("James", "Kirk", LocalDate.of(1955, 7, 1));
  }

}

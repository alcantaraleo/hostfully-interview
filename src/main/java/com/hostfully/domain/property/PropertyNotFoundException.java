package com.hostfully.domain.property;

public class PropertyNotFoundException extends RuntimeException {

  public PropertyNotFoundException(String message) {
    super(message);
  }

}

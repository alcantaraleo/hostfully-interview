package com.hostfully.domain.property;

public class PropertyFixture {

  private PropertyFixture() {}

  public static Property createProperty(){
    return createProperty("Test Property");
  }

  public static Property createProperty(String name) {
    return new Property(null, name, null);
  }

}

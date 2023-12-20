package com.hostfully.domain.block;

import com.hostfully.domain.property.Property;
import java.time.LocalDate;

public class BlockFixture {

  private BlockFixture() {
  }

  public static Block createBlock(Property property, LocalDate startDate, LocalDate endDate) {
    return Block.builder().property(property).startDate(startDate).endDate(endDate).build();
  }

  public static Block createBlock(Property property) {
    return BlockFixture.createBlock(property, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 20));
  }

}

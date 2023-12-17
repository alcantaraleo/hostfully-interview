package com.hostfully.domain.property;

import static org.assertj.core.api.Assertions.assertThat;

import com.hostfully.AbstractIntegrationTest;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags(value = {@Tag("Integration"), @Tag("Property")})
class FindPropertyTest extends AbstractIntegrationTest {

  @Test
  @DisplayName("""
      Given existing property,
      should return it,
      when finding by its id
      """)
  void givenExistingPropertyShouldFindById() {
    //arrange
    final var existingProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty());

    //act
    final var result = this.findProperty.findById(existingProperty.getId());

    //assert
    assertThat(result).isNotNull().isNotEmpty().hasValueSatisfying(foundProperty -> {
      assertThat(foundProperty.getAlias()).isEqualTo(existingProperty.getAlias());
    });
  }

  @Test
  @DisplayName("""
      Given non existing property id
      should not found any property,
      when finding by that id
      """)
  void givenNonExistingPropertyIdShouldNotFindAnyProperties() {
    //arrange
    final var existingProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty());

    //act
    final var result = this.findProperty.findById(UUID.randomUUID());

    //assert
    assertThat(result).isNotNull().isEmpty();
  }

}

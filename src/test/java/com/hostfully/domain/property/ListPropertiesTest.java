package com.hostfully.domain.property;

import com.hostfully.AbstractIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags(value = {@Tag("Integration"), @Tag("Property")})
class ListPropertiesTest extends AbstractIntegrationTest {

  @Test
  @DisplayName("""
      Given existing Properties,
      should list them
      """)
  public void givenExistingPropertiesShouldListThem() {
    //arrange

    final var firstProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty("First Property"));
    final var secondProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty("Second Property"));
    final var thirdProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty("Third Property"));

    //act
    final var result = this.listProperties.listProperties();

    //assert
    Assertions.assertThat(result).isNotNull().isNotEmpty().hasSize(3)
        .containsExactlyInAnyOrder(firstProperty, secondProperty, thirdProperty);

  }

}

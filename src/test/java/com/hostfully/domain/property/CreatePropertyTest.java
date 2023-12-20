package com.hostfully.domain.property;

import static org.assertj.core.api.Assertions.assertThat;

import com.hostfully.AbstractIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

@Tags(value = {@Tag("Integration"), @Tag("Property")})
class CreatePropertyTest extends AbstractIntegrationTest {

  @Test
  @DisplayName("""
      Given valid property input,
      should create new Property
      """)
  void givenValidPropertyInputShouldCreateNewProperty() {

    //arrange
    final var property = PropertyFixture.createProperty();

    //act
    final var result = this.createProperty.createProperty(property);

    //assert
    assertThat(result).isNotNull().satisfies(savedProperty -> {
      assertThat(savedProperty.getId()).isNotNull();
      assertThat(savedProperty.getAlias()).isNotNull().isEqualTo(property.getAlias());
      assertThat(savedProperty.getCreatedAt()).isNotNull();
    });
  }

  @Test
  @DisplayName("""
      Given already existing property,
      should not create new Property,
      wth duplicated alias
      """)
  void givenAlreadyExistingPropertyShouldNotCreateWithDuplicatedAlias() {

    //arrange
    final var property = PropertyFixture.createProperty();
    this.createProperty.createProperty(property);

    //act
    Assertions.assertThatThrownBy(() -> this.createProperty.createProperty(property)).isInstanceOf(
        DataIntegrityViolationException.class);

    //assert

  }


}

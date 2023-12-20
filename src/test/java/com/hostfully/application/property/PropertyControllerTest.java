package com.hostfully.application.property;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;

import com.hostfully.AbstractIntegrationTest;
import com.hostfully.domain.property.PropertyFixture;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tags(value = {@Tag("Integration"), @Tag("Property")})
class PropertyControllerTest extends AbstractIntegrationTest {

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.standaloneSetup(
        new PropertyController(this.createProperty, this.listProperties, this.findProperty,
            this.propertyModelDTOMapper));
  }

  @Test
  @DisplayName("""
      When performing a POST request to Property,
      Should return newly created Property
      """)
  void whenPerformingPostRequestToPropertyControllerShouldCreateAndReturnNewlyCreatedProperty() {

    //arrange
    final var propertyDTO = new PropertyDTO(null, "REST Property", null);

    //act && assert

    with().contentType(ContentType.JSON).body(propertyDTO).when()
        .post("/properties")
        .then().statusCode(201);

  }

  @Test
  @DisplayName("""
      When performing a GET request to list properties,
      Should return all properties
      """)
  void whenListingAllPropertiesFromControllerShouldReturn() {

    //arrange
    this.createProperty.createProperty(
        PropertyFixture.createProperty("First Property"));
    this.createProperty.createProperty(
        PropertyFixture.createProperty("Second Property"));
    this.createProperty.createProperty(
        PropertyFixture.createProperty("Third Property"));

    //act && assert
    final var result = with().contentType(ContentType.JSON).when().get("/properties")
        .then().statusCode(200)
        .extract().body().jsonPath().getList(".", PropertyDTO.class);

    Assertions.assertThat(result).isNotNull().isNotEmpty().hasSize(3);

  }

  @Test
  @DisplayName("""
      Given existing property,
      When performing a GET request to find that property by id,
      Should return that property
      """)
  void givenExistingPropertyWhenFindingByItsIdThenShouldFindIt() {

    //arrange
    final var property = this.createProperty.createProperty(
        PropertyFixture.createProperty("First Property"));

    //act && assert
    with().pathParam("propertyId", property.getId().toString())
        .contentType(ContentType.JSON).when().get("/properties/{propertyId}")
        .then().statusCode(200)
        .body("alias", CoreMatchers.equalTo(property.getAlias()));

  }

}

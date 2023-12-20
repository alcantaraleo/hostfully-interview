package com.hostfully.application.blocks;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static org.assertj.core.api.Assertions.assertThat;

import com.hostfully.AbstractIntegrationTest;
import com.hostfully.application.block.BlockController;
import com.hostfully.application.block.BlockDTO;
import com.hostfully.domain.block.BlockFixture;
import com.hostfully.domain.property.Property;
import com.hostfully.domain.property.PropertyFixture;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Tags(value = {@Tag("Integration"), @Tag("Property")})
class BlocksControllerTest extends AbstractIntegrationTest {

  private Property testProperty;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.standaloneSetup(
        new BlockController(this.blockModelDTOMapper, this.registerBlock, this.listBlocks));
    testProperty = this.createProperty.createProperty(
        PropertyFixture.createProperty("Booking Test Property"));
  }

  @Test
  @DisplayName("""
      When performing a POST request to Register Blocks,
      Should return newly created Block
      """)
  void whenPerformingRegisterBlock_ShouldReturnSuccessfullyCreatedBlock() {

    //arrange

    final var blockDTO = BlockDTO.builder().propertyId(this.testProperty.getId())
        .startDate(LocalDate.of(2024, 1, 1)).endDate(LocalDate.of(2024, 1, 10)).build();

    //act && assert

    final var registeredBlock = with().contentType(ContentType.JSON).body(blockDTO).when()
        .post("/blocks")
        .then().statusCode(201).extract().body().jsonPath().getObject(".", BlockDTO.class);

    assertThat(registeredBlock).isNotNull();
    assertThat(registeredBlock.getId()).isNotNull();


  }

  @Test
  @DisplayName("""
      When performing a GET to list all blocks,
      Should return all registerd blocks
      """)
  void whenListingAllBlocks_ShouldReturnAllRegisteredBlocks() {

    //arrange

    final var block = this.blockRepository.save(BlockFixture.createBlock(this.testProperty));
    final var secondBlock = this.blockRepository.save(
        BlockFixture.createBlock(this.testProperty, LocalDate.of(2024, 2, 1),
            LocalDate.of(2024, 2, 10)));

    //act && assert

    final var registeredBlocks = with().contentType(ContentType.JSON).when()
        .get("/blocks")
        .then().statusCode(200).extract().body().jsonPath().getList(".", BlockDTO.class);

    assertThat(registeredBlocks).isNotNull().isNotEmpty().hasSize(2);

    final var blockIds = registeredBlocks.stream().map(BlockDTO::getId).toList();

    assertThat(blockIds).containsExactlyInAnyOrder(block.getId(), secondBlock.getId());


  }

}

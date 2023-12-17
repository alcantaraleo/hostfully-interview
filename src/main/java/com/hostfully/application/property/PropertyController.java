package com.hostfully.application.property;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.hostfully.domain.property.usecase.CreateProperty;
import com.hostfully.domain.property.usecase.FindProperty;
import com.hostfully.domain.property.usecase.ListProperties;
import com.hostfully.infrastructure.converters.property.PropertyModelDTOMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@Tag(name = "Property", description = "Controller responsible for managing Properties")
public class PropertyController {

  private final CreateProperty createProperty;

  private final ListProperties listProperties;
  private final FindProperty findProperty;
  private final PropertyModelDTOMapper propertyModelDTOMapper;

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createProperty(@RequestBody @Valid PropertyDTO propertyDTO) {
    final var property = this.createProperty.createProperty(
        this.propertyModelDTOMapper.fromDTO(propertyDTO));
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(property.getId()).toUri()).body(property);

  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PropertyDTO>> listProperties() {
    final var properties = this.listProperties.listProperties().stream()
        .map(this.propertyModelDTOMapper::fromModel).toList();
    if (properties.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(properties);
  }

  @GetMapping(value = "/{propertyId}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<PropertyDTO> findById(@PathVariable("propertyId") UUID propertyId) {
    final var optionalProperty = this.findProperty.findById(propertyId);

    return optionalProperty.map(
            property -> ResponseEntity.ok(this.propertyModelDTOMapper.fromModel(property)))
        .orElseGet(() -> ResponseEntity.notFound().build());

  }

}

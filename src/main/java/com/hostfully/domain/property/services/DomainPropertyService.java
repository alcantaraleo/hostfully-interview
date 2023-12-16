package com.hostfully.domain.property.services;

import com.hostfully.domain.property.Property;
import com.hostfully.domain.property.PropertyRepository;
import com.hostfully.domain.property.usecase.CreateProperty;
import com.hostfully.domain.property.usecase.FindProperty;
import com.hostfully.domain.property.usecase.ListProperties;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainPropertyService implements CreateProperty, FindProperty, ListProperties {

  private final PropertyRepository propertyRepository;

  @Override
  public Property createProperty(Property property) {
    return this.propertyRepository.save(property);
  }

  @Override
  public Optional<Property> findById(UUID propertyId) {
    return this.propertyRepository.findById(propertyId);
  }

  @Override
  public List<Property> listProperties() {
    return this.propertyRepository.findAll();
  }
}

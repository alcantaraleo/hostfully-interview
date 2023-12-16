package com.hostfully.domain.property;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyRepository {

  Property save(Property property);

  Optional<Property> findById(UUID propertyId);

  List<Property> findAll();


}

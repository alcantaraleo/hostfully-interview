package com.hostfully.domain.property.usecase;

import com.hostfully.domain.property.Property;
import java.util.Optional;
import java.util.UUID;

public interface FindProperty {

  Optional<Property> findById(UUID propertyId);

}

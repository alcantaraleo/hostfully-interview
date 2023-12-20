package com.hostfully.infrastructure.converters.property;

import com.hostfully.domain.property.Property;
import com.hostfully.infrastructure.database.property.PropertyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PropertyModelEntityMapper {

  Property fromEntity(PropertyEntity entity);
  PropertyEntity fromModel(Property model);

}

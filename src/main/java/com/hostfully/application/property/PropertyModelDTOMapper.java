package com.hostfully.application.property;

import com.hostfully.application.property.PropertyDTO;
import com.hostfully.domain.property.Property;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyModelDTOMapper {

  Property fromDTO(PropertyDTO dto);
  PropertyDTO fromModel(Property model);

}

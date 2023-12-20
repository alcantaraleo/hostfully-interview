package com.hostfully.application.booking;

import com.hostfully.domain.booking.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingModelDTOMapper {

  @Mapping(target = "property.id", source = "propertyId")
  @Mapping(target = "status", defaultValue = "SCHEDULED")
  Booking fromDTO(BookingDTO dto);

  @Mapping(target = "propertyId", source = "property.id")
  BookingDTO fromModel(Booking model);

}

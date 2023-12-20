package com.hostfully.infrastructure.converters.booking;

import com.hostfully.domain.booking.Booking;
import com.hostfully.infrastructure.database.booking.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingModelEntityMapper {

  @Mapping(source = "propertyId", target = "property.id")
  @Mapping(source = "guestFirstName", target = "guest.firstName")
  @Mapping(source = "guestLastName", target = "guest.lastName")
  @Mapping(source = "guestDOB", target = "guest.dateOfBirth")
  Booking fromEntity(BookingEntity entity);

  @Mapping(target = "propertyId", source = "property.id")
  @Mapping(target = "guestFirstName", source = "guest.firstName")
  @Mapping(target = "guestLastName", source = "guest.lastName")
  @Mapping(target = "guestDOB", source = "guest.dateOfBirth")
  BookingEntity fromModel(Booking model);

}

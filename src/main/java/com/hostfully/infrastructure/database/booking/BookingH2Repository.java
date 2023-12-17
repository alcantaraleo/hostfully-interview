package com.hostfully.infrastructure.database.booking;

import com.hostfully.domain.booking.BookingStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingH2Repository extends JpaRepository<BookingEntity, UUID> {

  List<BookingEntity> findByPropertyIdAndStatusIn(UUID propertyId, List<BookingStatus> statuses);

  List<BookingEntity> findByPropertyId(UUID propertyId);

}

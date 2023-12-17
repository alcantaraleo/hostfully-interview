package com.hostfully.infrastructure.database.booking;

import com.hostfully.domain.booking.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "BOOKING")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookingEntity {

  @Id
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalDate endDate;

  @Column(nullable = false)
  private BookingStatus status;

  @Column(nullable = false)
  private UUID propertyId;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime lastUpdatedAt;

  private LocalDate cancellationDate;

  @Column(nullable = false)
  private String guestFirstName;

  @Column(nullable = false)
  private String guestLastName;

  private LocalDate guestDOB;

}

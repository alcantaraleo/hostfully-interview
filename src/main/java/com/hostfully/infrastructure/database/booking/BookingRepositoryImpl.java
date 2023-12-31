package com.hostfully.infrastructure.database.booking;

import com.hostfully.domain.booking.Booking;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.BookingStatus;
import com.hostfully.infrastructure.converters.booking.BookingModelEntityMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

  private final BookingModelEntityMapper modelEntityMapper;
  private final BookingH2Repository bookingH2Repository;

  @Override
  public Optional<Booking> findById(UUID bookingId) {
    return this.bookingH2Repository.findById(bookingId).map(this.modelEntityMapper::fromEntity);
  }

  @Override
  public Booking save(Booking booking) {
    final var bookingEntity = this.modelEntityMapper.fromModel(booking);
    bookingEntity.setId(UUID.randomUUID());
    bookingEntity.setCreatedAt(LocalDateTime.now());
    this.bookingH2Repository.save(bookingEntity);
    return this.modelEntityMapper.fromEntity(bookingEntity);
  }

  @Override
  public Booking update(Booking booking) {
    final var bookingEntity = this.modelEntityMapper.fromModel(booking);
    bookingEntity.setLastUpdatedAt(LocalDateTime.now());
    this.bookingH2Repository.save(bookingEntity);
    return this.modelEntityMapper.fromEntity(bookingEntity);
  }

  @Override
  public List<Booking> findAll() {
    return this.bookingH2Repository.findAll().stream().map(this.modelEntityMapper::fromEntity)
        .toList();
  }

  @Override
  public List<Booking> findByPropertyId(UUID propertyId) {
    return this.bookingH2Repository.findByPropertyId(propertyId).stream()
        .map(this.modelEntityMapper::fromEntity).toList();
  }

  @Override
  public List<Booking> findByPropertyIdAndStatus(UUID propertyId, List<BookingStatus> statuses) {
    return this.bookingH2Repository.findByPropertyIdAndStatusIn(propertyId, statuses).stream()
        .map(this.modelEntityMapper::fromEntity).toList();
  }
}

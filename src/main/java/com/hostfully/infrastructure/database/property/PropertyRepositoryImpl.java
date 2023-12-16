package com.hostfully.infrastructure.database.property;

import com.hostfully.domain.property.Property;
import com.hostfully.domain.property.PropertyRepository;
import com.hostfully.infrastructure.converters.property.PropertyModelEntityMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PropertyRepositoryImpl implements PropertyRepository {

  private final PropertyH2Repository propertyH2Repository;
  private final PropertyModelEntityMapper propertyModelEntityMapper;

  @Override
  public Property save(@NonNull Property property) {
    final var propertyEntity = propertyModelEntityMapper.fromModel(property);
    propertyEntity.setId(UUID.randomUUID());
    propertyEntity.setCreatedAt(LocalDateTime.now());
    this.propertyH2Repository.save(propertyEntity);
    return this.propertyModelEntityMapper.fromEntity(propertyEntity);
  }

  @Override
  public Optional<Property> findById(UUID propertyId) {
    return this.propertyH2Repository.findById(propertyId)
        .map(this.propertyModelEntityMapper::fromEntity);

  }

  @Override
  public List<Property> findAll() {
    return this.propertyH2Repository.findAll().stream()
        .map(this.propertyModelEntityMapper::fromEntity).toList();
  }
}

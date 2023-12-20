package com.hostfully.infrastructure.database.block;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockH2Repository extends JpaRepository<BlockEntity, UUID> {

  List<BlockEntity> findByPropertyId(UUID propertyId);

}

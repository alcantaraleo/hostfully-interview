package com.hostfully.infrastructure.database.property;

import com.hostfully.infrastructure.database.property.PropertyEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface PropertyH2Repository extends JpaRepository<PropertyEntity, UUID> {

}

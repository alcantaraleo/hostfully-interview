package com.hostfully.domain.block;

import java.util.List;
import java.util.UUID;

public interface BlockRepository {

  Block save(Block block);

  List<Block> findAll();

  List<Block> findByPropertyId(UUID propertyId);

}

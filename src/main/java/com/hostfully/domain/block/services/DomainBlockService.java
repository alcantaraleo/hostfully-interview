package com.hostfully.domain.block.services;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.block.BlockRepository;
import com.hostfully.domain.block.usecases.ListBlocks;
import com.hostfully.domain.block.usecases.RegisterBlock;
import com.hostfully.domain.property.PropertyNotFoundException;
import com.hostfully.domain.property.PropertyRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainBlockService implements RegisterBlock, ListBlocks {

  private final BlockRepository blockRepository;

  private final PropertyRepository propertyRepository;

  public List<Block> findBlocksByProperty(UUID propertyId) {
    return this.blockRepository.findByPropertyId(propertyId);
  }

  public Block registerBlock(Block block) {
    final var propertyId = block.getProperty().getId();
    final var optionalProperty = this.propertyRepository.findById(propertyId);

    if (optionalProperty.isEmpty()) {
      log.info("Supplied Property identifier {} could not be found.", propertyId);
      throw new PropertyNotFoundException(
          "Property with identifier " + propertyId + " could not be found");
    }

    return this.blockRepository.save(block);

  }

  @Override
  public List<Block> listBlocks() {
    return this.blockRepository.findAll();
  }
}

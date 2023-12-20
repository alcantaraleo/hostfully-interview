package com.hostfully.infrastructure.database.block;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.block.BlockRepository;
import com.hostfully.infrastructure.converters.block.BlockModelEntityMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlockRepositoryImpl implements BlockRepository {

  private final BlockH2Repository blockH2Repository;

  private final BlockModelEntityMapper blockModelEntityMapper;

  @Override
  public Block save(Block block) {
    final var blockEntity = this.blockModelEntityMapper.fromModel(block);
    blockEntity.setId(UUID.randomUUID());
    blockEntity.setCreatedAt(LocalDateTime.now());
    return this.blockModelEntityMapper.fromEntity(
        this.blockH2Repository.save(blockEntity));
  }

  @Override
  public List<Block> findAll() {
    return this.blockH2Repository.findAll().stream().map(this.blockModelEntityMapper::fromEntity)
        .toList();
  }

  @Override
  public List<Block> findByPropertyId(UUID propertyId) {
    return this.blockH2Repository.findByPropertyId(propertyId).stream()
        .map(this.blockModelEntityMapper::fromEntity).toList();
  }
}

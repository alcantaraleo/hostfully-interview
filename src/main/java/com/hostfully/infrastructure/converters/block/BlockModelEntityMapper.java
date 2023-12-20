package com.hostfully.infrastructure.converters.block;

import com.hostfully.domain.block.Block;
import com.hostfully.infrastructure.database.block.BlockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface BlockModelEntityMapper {

  @Mapping(target = "propertyId", source = "property.id")
  public BlockEntity fromModel(Block model);

  @Mapping(source = "propertyId", target = "property.id")
  public Block fromEntity(BlockEntity entity);

}

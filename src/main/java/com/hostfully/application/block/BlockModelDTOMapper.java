package com.hostfully.application.block;

import com.hostfully.domain.block.Block;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlockModelDTOMapper {

  @Mapping(target = "propertyId", source = "property.id")
  BlockDTO fromModel(Block block);

  @Mapping(target = "property.id", source = "propertyId")
  Block fromDTO(BlockDTO dto);

}

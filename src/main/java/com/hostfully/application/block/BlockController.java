package com.hostfully.application.block;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.hostfully.domain.block.usecases.ListBlocks;
import com.hostfully.domain.block.usecases.RegisterBlock;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/blocks")
@RequiredArgsConstructor
@Tag(name = "Block", description = "Controller responsible for managing Blocks")
public class BlockController {

  private final BlockModelDTOMapper blockModelDTOMapper;
  private final RegisterBlock registerBlock;

  private final ListBlocks listBlocks;

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<BlockDTO> registerBlock(@RequestBody @Valid BlockDTO blockDTO) {
    final var block = this.registerBlock.registerBlock(this.blockModelDTOMapper.fromDTO(blockDTO));
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(block.getId()).toUri()).body(this.blockModelDTOMapper.fromModel(block));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BlockDTO>> listBlocks() {
    final var blocks = this.listBlocks.listBlocks();
    if (blocks.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(blocks.stream().map(this.blockModelDTOMapper::fromModel).toList());
  }

}

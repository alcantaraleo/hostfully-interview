package com.hostfully.domain.block.services;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.block.validators.BlockValidation;
import com.hostfully.domain.booking.Booking;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainBlockValidationService {

  private final List<BlockValidation> validations;

  public boolean validate(Booking proposedBooking, List<Block> existingBlocks) {
    log.info("Validating proposed booking {} against blocks", proposedBooking);

    return this.validations.stream()
        .map(validation -> validation.isValid(proposedBooking, existingBlocks))
        .noneMatch(result -> result == false);

  }

}

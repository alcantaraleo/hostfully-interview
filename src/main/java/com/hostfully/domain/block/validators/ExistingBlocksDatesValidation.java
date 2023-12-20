package com.hostfully.domain.block.validators;

import com.hostfully.domain.block.Block;
import com.hostfully.domain.booking.Booking;
import java.util.List;
import java.util.function.BiPredicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExistingBlocksDatesValidation implements BlockValidation {

  private static final BiPredicate<Booking, Block> OVERLAPPING_DATES_PREDICATE = (proposedBooking, existingBlocking) ->
      (proposedBooking.getStartDate().isBefore(existingBlocking.getEndDate()) &&
          proposedBooking.getEndDate().isAfter(existingBlocking.getStartDate()));

  @Override
  public boolean isValid(Booking proposedBooking, List<Block> existingBlocks) {
    log.info("Validating booking {} for overlapping dates with existing blocks",
        proposedBooking);

    return existingBlocks.stream().noneMatch(
        existingBlock -> OVERLAPPING_DATES_PREDICATE.test(proposedBooking, existingBlock));
  }
}

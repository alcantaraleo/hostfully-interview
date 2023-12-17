package com.hostfully.application.booking;

import com.hostfully.domain.booking.usecases.RegisterBooking;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Controller responsible for managing Bookings")
public class BookingController {

  private final BookingModelDTOMapper modelDTOMapper;
  private final RegisterBooking registerBooking;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Object> registerBooking(@RequestBody @Valid BookingDTO bookingDTO) {
    final var booking = this.registerBooking.registerBooking(
        this.modelDTOMapper.fromDTO(bookingDTO));
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(booking.getId()).toUri()).body(this.modelDTOMapper.fromModel(booking));
  }

}

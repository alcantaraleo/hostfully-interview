package com.hostfully.application.booking;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.hostfully.domain.booking.usecases.CancelBooking;
import com.hostfully.domain.booking.usecases.ListBookings;
import com.hostfully.domain.booking.usecases.RegisterBooking;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final ListBookings listBookings;

  private final CancelBooking cancelBooking;

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  ResponseEntity<Object> registerBooking(@RequestBody @Valid BookingDTO bookingDTO) {
    final var booking = this.registerBooking.registerBooking(
        this.modelDTOMapper.fromDTO(bookingDTO));
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(booking.getId()).toUri()).body(this.modelDTOMapper.fromModel(booking));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  ResponseEntity<List<BookingDTO>> listBookings() {
    final var bookings = this.listBookings.listBookings().stream()
        .map(this.modelDTOMapper::fromModel).toList();
    if (bookings.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(bookings);
  }

  @PatchMapping(value = "/{bookingId}/cancel", produces = APPLICATION_JSON_VALUE)
  ResponseEntity<BookingDTO> cancelBooking(@PathVariable("bookingId") UUID bookingId) {
    final var toBeCancelledBookingDTO = BookingDTO.createFromId(bookingId);
    final var cancelledBooking = this.cancelBooking.cancelBooking(
        this.modelDTOMapper.fromDTO(toBeCancelledBookingDTO));
    return ResponseEntity.ok(this.modelDTOMapper.fromModel(cancelledBooking));
  }


}

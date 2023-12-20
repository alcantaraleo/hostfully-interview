package com.hostfully;

import com.hostfully.application.booking.BookingModelDTOMapper;
import com.hostfully.application.guest.GuestDTO;
import com.hostfully.application.property.PropertyModelDTOMapper;
import com.hostfully.domain.booking.BookingRepository;
import com.hostfully.domain.booking.usecases.CancelBooking;
import com.hostfully.domain.booking.usecases.ListBookings;
import com.hostfully.domain.booking.usecases.RebookBooking;
import com.hostfully.domain.booking.usecases.RegisterBooking;
import com.hostfully.domain.booking.usecases.UpdateGuestDetails;
import com.hostfully.domain.property.usecase.CreateProperty;
import com.hostfully.domain.property.usecase.FindProperty;
import com.hostfully.domain.property.usecase.ListProperties;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/clean-db.sql")
public abstract class AbstractIntegrationTest {

  @LocalServerPort
  protected int port;

  @Autowired
  protected CreateProperty createProperty;

  @Autowired
  protected ListProperties listProperties;

  @Autowired
  protected FindProperty findProperty;

  @Autowired
  protected RegisterBooking registerBooking;

  @Autowired
  protected ListBookings listBookings;

  @Autowired
  protected UpdateGuestDetails updateGuestDetails;

  @Autowired
  protected CancelBooking cancelBooking;

  @Autowired
  protected RebookBooking rebookBooking;

  @Autowired
  protected PropertyModelDTOMapper propertyModelDTOMapper;

  @Autowired
  protected BookingModelDTOMapper bookingModelDTOMapper;

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected BookingRepository bookingRepository;

  protected GuestDTO buildGuestDTO() {
    return GuestDTO.builder().firstName("Booking Test Guest")
        .lastName("Booking Test Guest Last Name").dateOfBirth(LocalDate.now())
        .build();
  }

}

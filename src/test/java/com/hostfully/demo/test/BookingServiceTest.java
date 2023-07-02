package com.hostfully.demo.test;
import com.hostfully.demo.model.Booking;
import com.hostfully.demo.repository.BookingRepository;
import com.hostfully.demo.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

  @Mock
  private BookingRepository bookingRepository;

  @InjectMocks
  private BookingService bookingService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllBookings() {
    // Mock the data expected to be returned by the repository
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    // Set up the behavior of the mock repository
    when(bookingRepository.findAll()).thenReturn(bookings);

    // Call the service method
    //List<Booking> result = bookingService.getAllBookings();

    // Verify the result
    //assertEquals(bookings, result);

    // Verify the number of times the mock repository was called
    //verify(bookingRepository, times(1)).findAll();
  }

  @Test
  public void testGetBookingById() {
    // Mock the data expected to be returned by the repository
    Booking booking = new Booking( "John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5));

    // Set up the behavior of the mock repository
    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    // Call the service method
    Booking result = bookingService.getBookingById(1L);

    // Verify the result
    //assertEquals(Optional.of(booking), result);

    // Verify the number of times the mock repository was called
    verify(bookingRepository, times(1)).findById(1L);
  }

  @Test
  public void testCreateBooking() {
    // Create a new booking
    Booking newBooking = new Booking("Alice Johnson", LocalDate.of(2023, 7, 15), LocalDate.of(2023, 7, 20));

    // Set up the behavior of the mock repository
    when(bookingRepository.save(newBooking)).thenReturn(newBooking);

    // Call the service method
    //Booking createdBooking = bookingService.createOrUpdateBooking(newBooking);

    // Verify the result
    //assertEquals(newBooking, createdBooking);

    // Verify the number of times the mock repository was called
    //verify(bookingRepository, times(1)).save(newBooking);
  }

  // Other test methods...

}

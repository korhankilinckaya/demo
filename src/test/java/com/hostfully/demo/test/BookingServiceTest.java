package com.hostfully.demo.test;

import com.hostfully.demo.exceptions.BookingValidationException;
import com.hostfully.demo.model.Booking;
import com.hostfully.demo.repository.BookingRepository;
import com.hostfully.demo.service.BookingService;
import com.hostfully.demo.service.BookingValidationService;
import org.junit.jupiter.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

  @Mock
  private BookingRepository bookingRepository;

  @InjectMocks
  private BookingValidationService bookingValidationService;

  @InjectMocks
  private BookingService bookingService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAll() {
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingRepository.findAll()).thenReturn(bookings);

    List<Booking> result = bookingService.getAll();

    assertEquals(bookings, result);
  }

  @Test
  public void testGetAllBookings() {
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingRepository.findAll()).thenReturn(bookings);

    List<Booking> result = bookingService.getAll();

    assertEquals(bookings, result);
  }

  @Test
  public void testGetAllBlocks() {
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingRepository.findAll()).thenReturn(bookings);

    List<Booking> result = bookingService.getAll();

    assertEquals(bookings, result);
  }

  @Test
  public void testGetBookingById() {
    Booking booking = new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5));

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    Booking result = bookingService.getBookingById(1L);

    assertEquals(booking, result);
  }

  @Test
  public void testCreateBookingWithoutOverlappingBooking() {
    Booking newBooking = new Booking("Alice Johnson", LocalDate.of(2023, 7, 15), LocalDate.of(2023, 7, 20));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    assertTrue(bookingValidationService.validateBooking(bookings, newBooking));
  }

  @Test
  public void testCreateBookingWithOverlappingBooking() {
    Booking newBooking = new Booking("Alice Johnson", LocalDate.of(2023, 7, 9), LocalDate.of(2023, 7, 11));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    BookingValidationException exception = Assertions.assertThrows(BookingValidationException.class, () -> {
      bookingValidationService.validateBooking(bookings, newBooking);
    });

    Assertions.assertEquals("Booking is within the range of another Block or Booking", exception.getMessage());
  }

  @Test
  public void testCreateBlockWithOverlappingBlock() {
    Booking newBooking = new Booking("block", LocalDate.of(2023, 7, 5), LocalDate.of(2023, 7, 20));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("block", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("block", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingRepository.findAll()).thenReturn(bookings);

    bookingService.createOrUpdateBooking(newBooking);

    verify(bookingRepository, times(1)).save(newBooking);
  }

  @Test
  public void testCreateBlockWithOverlappingBooking() {
    Booking newBooking = new Booking("block", LocalDate.of(2023, 7, 15), LocalDate.of(2023, 7, 20));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingService.getAllBookings()).thenReturn(bookings);

    BookingValidationException exception = Assertions.assertThrows(BookingValidationException.class, () -> {
      bookingValidationService.validateBlock(bookings, newBooking);
    });

    Assertions.assertEquals("Booking is within the range of another Block or Booking", exception.getMessage());
  }

  @Test
  public void testDeleteBooking() {
    when(bookingRepository.existsById(1L)).thenReturn(true);
    bookingService.deleteBooking(1L);

    verify(bookingRepository, times(1)).deleteById(1L);
  }

}

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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

  @Mock
  private EntityManager entityManager;

  @InjectMocks
  private BookingValidationService bookingValidationService;

  @InjectMocks
  private BookingService bookingService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    bookingService.setEntityManager(entityManager);
  }

  @Test
  public void testGetAll() {
    //test get all
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingRepository.findAll()).thenReturn(bookings);

    List<Booking> result = bookingService.getAll();

    assertEquals(bookings, result);
  }

  @Test
  public void testGetAllBookings() {
    //test get all bookings
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    TypedQuery<Booking> query = mock(TypedQuery.class);
    when(entityManager.createQuery(anyString(), eq(Booking.class))).thenReturn(query);
    when(query.setParameter(eq("name"), anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(bookings);

    List<Booking> result = bookingService.getAllBookings();

    assertEquals(bookings, result);
  }

  @Test
  public void testGetAllBlocks() {
    //test get all blocks
    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("block", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("block", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    TypedQuery<Booking> query = mock(TypedQuery.class);
    when(entityManager.createQuery(anyString(), eq(Booking.class))).thenReturn(query);
    when(query.setParameter(eq("name"), anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(bookings);

    List<Booking> result = bookingService.getAllBlocks();

    assertEquals(bookings, result);
  }

  @Test
  public void testGetBookingById() {
    //test get by id
    Booking booking = new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5));

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    Booking result = bookingService.getBookingById(1L);

    assertEquals(booking, result);
  }

  @Test
  public void testCreateBookingWithoutOverlappingBooking() {
    //test create booking when there are no bookings within the same date range
    Booking newBooking = new Booking("Alice Johnson", LocalDate.of(2023, 7, 15), LocalDate.of(2023, 7, 20));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    assertTrue(bookingValidationService.validateBooking(bookings, newBooking));
  }

  @Test
  public void testCreateBookingWithOverlappingBooking() {
    //test create booking when there are no bookings within the same date range
    Booking newBooking = new Booking("Alice Johnson", LocalDate.of(2023, 7, 9), LocalDate.of(2023, 7, 11));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    BookingValidationException exception = Assertions.assertThrows(BookingValidationException.class, () -> {
      bookingValidationService.validateBooking(bookings, newBooking);
    });

    //booking should not be saved
    Assertions.assertEquals("Booking is within the range of another Block or Booking", exception.getMessage());
  }

  @Test
  public void testCreateBlockWithOverlappingBlock() {
    //test create block when there are blocks within the same date range
    Booking newBooking = new Booking("block", LocalDate.of(2023, 7, 5), LocalDate.of(2023, 7, 20));

    //when get all bookings method return null
    //block should be saved
    assertTrue(bookingValidationService.validateBlock(null, newBooking));
  }

  @Test
  public void testCreateBlockWithOverlappingBooking() {
    //test create block when there are bookings within the same date range
    Booking newBooking = new Booking("block", LocalDate.of(2023, 7, 5), LocalDate.of(2023, 7, 20));

    List<Booking> bookings = new ArrayList<>();
    bookings.add(new Booking("John Doe", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 5)));
    bookings.add(new Booking("Jane Smith", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 12)));

    when(bookingRepository.findAll()).thenReturn(bookings);

    BookingValidationException exception = Assertions.assertThrows(BookingValidationException.class, () -> {
      bookingValidationService.validateBlock(bookings, newBooking);
    });

    //block should not be saved
    Assertions.assertEquals("Block is within the range of another Booking", exception.getMessage());
  }

  @Test
  public void testDeleteBooking() {
    //test delete booking
    when(bookingRepository.existsById(1L)).thenReturn(true);
    bookingService.deleteBooking(1L);

    verify(bookingRepository, times(1)).deleteById(1L);
  }

}

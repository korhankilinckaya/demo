package com.hostfully.demo.controller;

import com.hostfully.demo.exceptions.BookingValidationException;
import com.hostfully.demo.model.Booking;
import com.hostfully.demo.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Booking API", description = "Endpoints for managing bookings")
public class BookingController {
  private final BookingService bookingService;

  @Autowired
  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  @Operation(summary = "Get all bookings and blocks")
  public ResponseEntity<List<Booking>> getAll() {
    List<Booking> bookings = bookingService.getAll();
    if (bookings != null) {
      return ResponseEntity.ok(bookings);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/bookings")
  @Operation(summary = "Get all bookings")
  public ResponseEntity<List<Booking>> getAllBookings() {
    List<Booking> bookings = bookingService.getAllBookings();
    if (bookings != null) {
      return ResponseEntity.ok(bookings);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/blocks")
  @Operation(summary = "Get all blocks")
  public ResponseEntity<List<Booking>> getAllBlocks() {
    List<Booking> bookings = bookingService.getAllBlocks();
    if (bookings != null) {
      return ResponseEntity.ok(bookings);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a booking by ID")
  public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
    Booking booking = bookingService.getBookingById(id);
    if (booking != null) {
      return ResponseEntity.ok(booking);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  @Operation(summary = "Create a new booking", description = "Creates a new booking")
  public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
    Booking createdBooking;
    if ("block".equalsIgnoreCase(booking.getGuestName())) {
      createdBooking = bookingService.createOrUpdateBlock(booking);
    } else {
      createdBooking = bookingService.createOrUpdateBooking(booking);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing booking", description = "Updates an existing booking based on the given ID")
  public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
    Booking updatedBooking = bookingService.getBookingById(id);
    Booking copiedBooking = new Booking();
    BeanUtils.copyProperties(updatedBooking, copiedBooking);

    if (copiedBooking != null) {
      if ("block".equalsIgnoreCase(copiedBooking.getGuestName())) {
        copiedBooking.setStartDate(booking.getStartDate());
        copiedBooking.setEndDate(booking.getEndDate());
        copiedBooking = bookingService.createOrUpdateBlock(copiedBooking);
      } else {
        copiedBooking.setGuestName(booking.getGuestName());
        copiedBooking.setStartDate(booking.getStartDate());
        copiedBooking.setEndDate(booking.getEndDate());
        copiedBooking = bookingService.createOrUpdateBooking(copiedBooking);
      }
    } else {
      copiedBooking = bookingService.createOrUpdateBooking(booking);
    }

    return ResponseEntity.ok(copiedBooking);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a booking", description = "Deletes a booking based on the given ID")
  public ResponseEntity<Void> deleteBooking(@PathVariable("id") long id) {
    boolean deleted = bookingService.deleteBooking(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @ExceptionHandler(BookingValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleValidationException(BookingValidationException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
package com.hostfully.demo.controller;

import com.hostfully.demo.exceptions.BookingValidationException;
import com.hostfully.demo.model.Booking;
import com.hostfully.demo.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking API", description = "Endpoints for managing bookings")
public class BookingController {
  private final BookingService bookingService;

  @Autowired
  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  @Operation(summary = "Get all bookings")
  public List<Booking> getAllBookings() {
    return bookingService.getAllBookings();
  }

  @GetMapping("/blocks")
  @Operation(summary = "Get all blocks")
  public List<Booking> getAllBlocks() {
    return bookingService.getAllBlocks();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a booking by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Booking found",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Booking.class)) }),
          @ApiResponse(responseCode = "404", description = "Booking not found")
  })
  public ResponseEntity<Booking>  getBookingById(@PathVariable Long id) {
    Booking booking = bookingService.getBookingById(id);
    if (booking != null) {
      return ResponseEntity.ok(booking);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  @Operation(summary = "Create a new booking", description = "Creates a new booking")
  @ApiResponse(responseCode = "201", description = "Booking created", content = @Content(schema = @Schema(implementation = Booking.class)))
  public ResponseEntity<Booking>  createBooking(@RequestBody Booking booking) {
    Booking createdBooking;
    if("block".equalsIgnoreCase(booking.getGuestName())){
      createdBooking = bookingService.createOrUpdateBlock(booking);
    } else {
      createdBooking = bookingService.createOrUpdateBooking(booking);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing booking", description = "Updates an existing booking based on the given ID")
  @ApiResponse(responseCode = "200", description = "Booking updated", content = @Content(schema = @Schema(implementation = Booking.class)))
  @ApiResponse(responseCode = "404", description = "Booking not found")
  public ResponseEntity<Booking>  updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
    Booking updatedBooking = bookingService.getBookingById(id);

    if(updatedBooking != null) {
      if("block".equalsIgnoreCase(updatedBooking.getGuestName())){
        updatedBooking.setStartDate(booking.getStartDate());
        updatedBooking.setEndDate(booking.getEndDate());
        updatedBooking = bookingService.createOrUpdateBlock(updatedBooking);
      } else {
        updatedBooking.setGuestName(booking.getGuestName());
        updatedBooking.setStartDate(booking.getStartDate());
        updatedBooking.setEndDate(booking.getEndDate());
        updatedBooking = bookingService.createOrUpdateBooking(updatedBooking);
      }
    } else {
      updatedBooking = bookingService.createOrUpdateBooking(booking);
    }

    return ResponseEntity.ok(updatedBooking);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a booking", description = "Deletes a booking based on the given ID")
  @ApiResponse(responseCode = "204", description = "Booking deleted")
  @ApiResponse(responseCode = "404", description = "Booking not found")
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
package com.hostfully.demo.controller;

import com.hostfully.demo.model.Booking;
import com.hostfully.demo.repository.BookingRepository;
import com.hostfully.demo.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackageClasses = BookingRepository.class)
@RequestMapping("/api")
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

  @GetMapping("/{id}")
  @Operation(summary = "Get a booking by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Booking found",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Booking.class)) }),
          @ApiResponse(responseCode = "404", description = "Booking not found")
  })
  public Booking getBookingById(@PathVariable Long id) {
    return bookingService.getBookingById(id);
  }

  @PostMapping
  @Operation(summary = "Create a new booking")
  public Booking createBooking(@RequestBody Booking booking) {
    return bookingService.createBooking(booking);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a booking")
  public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
    Booking bookingToBeUpdated = bookingService.getBookingById(id);
    bookingToBeUpdated.setGuestName(booking.getGuestName());
    bookingToBeUpdated.setStartDate(booking.getStartDate());
    bookingToBeUpdated.setEndDate(booking.getEndDate());
    return bookingService.updateBooking(bookingToBeUpdated);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a booking")
  public void deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
  }
}
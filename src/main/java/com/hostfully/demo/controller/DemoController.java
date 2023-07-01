package com.hostfully.demo.controller;

import com.hostfully.demo.model.Booking;
import com.hostfully.demo.repository.BookingRepository;
import com.hostfully.demo.service.BookingService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@EnableJpaRepositories(basePackageClasses = BookingRepository.class)
public class DemoController {
  private final BookingService bookingService;

  @Autowired
  public DemoController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  public List<Booking> getAllBookings() {
    return bookingService.getAllBookings();
  }

  @GetMapping("/{id}")
  public Booking getBookingById(@PathVariable Long id) {
    return bookingService.getBookingById(id);
  }

  @PostMapping
  public Booking createBooking(@RequestBody Booking booking) {
    return bookingService.createBooking(booking);
  }

  @PutMapping("/{id}")
  public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
    Booking bookingToBeUpdated = bookingService.getBookingById(id);
    bookingToBeUpdated.setName(booking.getName());
    bookingToBeUpdated.setStartDate(booking.getStartDate());
    bookingToBeUpdated.setEndDate(booking.getEndDate());
    return bookingService.updateBooking(bookingToBeUpdated);
  }

  @DeleteMapping("/{id}")
  public void deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
  }
}
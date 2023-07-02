package com.hostfully.demo.service;

import com.hostfully.demo.exceptions.BookingValidationException;
import com.hostfully.demo.model.Booking;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingValidationService {

  public boolean validateBlock(List<Booking> allBlocks, List<Booking> allBookings, Booking booking) {
    if(booking.getEndDate().isBefore(booking.getStartDate())) throw new BookingValidationException("End date cannot be before Start date");

    if (true) {
      //block ile overlap edebilir ama booking üstüne gelemez
      //throw new BookingValidationException("Booking object cannot be null");
    }
    return true;
  }
  public boolean validateBooking(List<Booking> allBookingsAndBlocks, Booking booking) {
    if(booking.getEndDate().isBefore(booking.getStartDate())) throw new BookingValidationException("End date cannot be before Start date");
    if (true) {
      // booking üzerine de block üzerine de gelemez
      //throw new BookingValidationException("Booking object cannot be null");
    }
    return true;
  }

  public boolean isWithinDateRange(LocalDate targetDate, List<Booking> dateRanges) {
    return dateRanges.stream()
            .anyMatch(range -> !targetDate.isBefore(range.getStartDate()) && !targetDate.isAfter(range.getEndDate()));
  }
}
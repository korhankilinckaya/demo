package com.hostfully.demo.service;

import com.hostfully.demo.exceptions.BookingValidationException;
import com.hostfully.demo.model.Booking;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingValidationService {

  public boolean validateBlock(List<Booking> allBookings, Booking booking) {
    if(allBookings == null) return true;

    if (booking.getEndDate().isBefore(booking.getStartDate()))
      throw new BookingValidationException("End date cannot be before Start date");
    if (overlappingBooking(booking, allBookings))
      throw new BookingValidationException("Block is within the range of another Booking");

    return true;
  }

  public boolean validateBooking(List<Booking> allBookingsAndBlocks, Booking booking) {
    if(allBookingsAndBlocks == null) return true;

    if (booking.getEndDate().isBefore(booking.getStartDate()))
      throw new BookingValidationException("End date cannot be before Start date");
    if (overlappingBooking(booking, allBookingsAndBlocks))
      throw new BookingValidationException("Booking is within the range of another Block or Booking");

    return true;
  }

  //date overlapping check
  public boolean overlappingBooking(Booking target, List<Booking> allBookings) {
    return allBookings.stream()
            .anyMatch(range ->
                    target.getStartDate().isEqual(range.getStartDate()) ||
                            target.getEndDate().isEqual(range.getEndDate()) ||
                            target.getStartDate().isEqual(range.getEndDate()) ||
                            target.getEndDate().isEqual(range.getStartDate()) ||
                            (target.getStartDate().isAfter(range.getStartDate()) && target.getStartDate().isBefore(range.getEndDate())) ||
                            (target.getEndDate().isAfter(range.getStartDate()) && target.getEndDate().isBefore(range.getEndDate())) ||
                            (target.getStartDate().isBefore(range.getStartDate()) && target.getEndDate().isAfter(range.getEndDate())) ||
                            (target.getStartDate().isAfter(range.getStartDate()) && target.getEndDate().isBefore(range.getEndDate()))
            );
  }
}
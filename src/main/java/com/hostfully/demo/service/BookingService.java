package com.hostfully.demo.service;
import com.hostfully.demo.model.Booking;
import com.hostfully.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
  private final BookingRepository bookingRepository;

  @Autowired
  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  public Booking getBookingById(Long id) {
    return bookingRepository.findById(id).orElse(null);
  }

  public Booking createBooking(Booking booking) {
    return bookingRepository.save(booking);
  }

  public Booking updateBooking(Booking booking) {
    return bookingRepository.save(booking);
  }

  public void deleteBooking(Long id) {
    bookingRepository.deleteById(id);
  }
}
package com.hostfully.demo.service;

import com.hostfully.demo.model.Booking;
import com.hostfully.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BookingService {

  @PersistenceContext
  private EntityManager entityManager;

  private final BookingRepository bookingRepository;
  private final BookingValidationService bookingValidationService;

  @Autowired
  public BookingService(BookingRepository bookingRepository, BookingValidationService bookingValidationService) {
    this.bookingRepository = bookingRepository;
    this.bookingValidationService = bookingValidationService;
  }

  public List<Booking> getAll(){
    return bookingRepository.findAll();
  }

  public List<Booking> getAllBookings() {
    return entityManager.createQuery("SELECT b FROM Booking b WHERE b.guestName != :name", Booking.class)
            .setParameter("name", "block")
            .getResultList();
  }

  public List<Booking> getAllBlocks() {
    return entityManager.createQuery("SELECT b FROM Booking b WHERE b.guestName = :name", Booking.class)
            .setParameter("name", "block")
            .getResultList();
  }

  public Booking getBookingById(Long id) { return bookingRepository.findById(id).orElse(null); }

  public Booking createOrUpdateBooking(Booking booking) {
    if(bookingValidationService.validateBooking(this.getAllBookings(), booking)) {
      return bookingRepository.save(booking);
    }
    return null;
  }

  public Booking createOrUpdateBlock(Booking booking) {
    if(bookingValidationService.validateBlock(this.getAllBlocks(), this.getAllBookings(), booking)) {
      return bookingRepository.save(booking);
    }
    return null;
  }

  public boolean deleteBooking(long id) {
    if (bookingRepository.existsById(id)) {
      bookingRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
package com.hostfully.demo.service;

import com.hostfully.demo.exceptions.BookingValidationException;
import com.hostfully.demo.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingValidationService {

  public boolean validateBlock(Booking booking) {
    if (true) {
      //block ile overlap edebilir ama booking üstüne gelemez
      //throw new BookingValidationException("Booking object cannot be null");
    }
    return true;
  }
  public boolean validateBooking(Booking booking) {
    if (true) {
      // booking üzerine de block üzerine de gelemez
      //throw new BookingValidationException("Booking object cannot be null");
    }
    return true;
  }
}
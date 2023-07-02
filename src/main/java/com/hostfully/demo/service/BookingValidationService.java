package com.hostfully.demo.service;

import com.hostfully.demo.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingValidationService {

  public void validateBooking(Booking booking) {
    if (booking.isBlock()) {
      // Burada block flag'i true olan rezervasyonları doğrulama işlemini gerçekleştirin.
      // İstediğiniz doğrulama kurallarını uygulayabilirsiniz.
      // Örneğin, startDate ve endDate arasında geçerli bir tarih aralığı olması gerektiğini kontrol edebilirsiniz.
      // Bu örnekte sadece basit bir çıktı veriliyor.
      System.out.println("Block flag'i true olan bir rezervasyon doğrulandı!");
    }
  }
}
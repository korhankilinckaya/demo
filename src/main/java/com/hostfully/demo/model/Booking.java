package com.hostfully.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Schema(name = "Booking", description = "Booking information")
public class Booking {
  public Booking(String guestName, LocalDate startDate, LocalDate endDate) {
    this.guestName = guestName;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID", hidden = true)
  private Long id;

  @Schema(description = "Guest Name", required = true)
  private String guestName;

  @Schema(description = "Start Date")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @Schema(description = "End Date")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;
}
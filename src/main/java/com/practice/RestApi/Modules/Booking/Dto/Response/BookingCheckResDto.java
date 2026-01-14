package com.practice.RestApi.Modules.Booking.Dto.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingCheckResDto {

    private Long bookingId;
    private String status;
    private String message;
    private String type;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String duration;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

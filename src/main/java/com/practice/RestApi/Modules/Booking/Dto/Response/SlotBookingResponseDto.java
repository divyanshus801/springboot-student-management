package com.practice.RestApi.Modules.Booking.Dto.Response;

import com.practice.RestApi.Modules.Booking.Entity.SlotBookingEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotBookingResponseDto {
    private Long id;
    private Long bookedUserId;
    private Long slotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SlotBookingEntity.BookingStatus bookingStatus;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

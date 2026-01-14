package com.practice.RestApi.Modules.Booking.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotBookingRequestDto {

    @NotNull(message = "Slot is Mandatory")
    private Long slotId;

    @NotNull(message = "Start Time is Mandatory")
    private LocalDateTime startTime;

    @NotNull(message = "End Time is Mandatory")
    private  LocalDateTime endTime;
}

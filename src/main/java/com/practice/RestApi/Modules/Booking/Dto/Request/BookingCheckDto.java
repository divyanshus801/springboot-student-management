package com.practice.RestApi.Modules.Booking.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingCheckDto {

    @NotNull(message = "booking Id is mandatory")
    private Long bookingId;

    @NotNull(message = "Type is Mandatory")
    private String type;

}

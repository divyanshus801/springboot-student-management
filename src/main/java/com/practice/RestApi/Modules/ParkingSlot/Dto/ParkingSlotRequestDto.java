package com.practice.RestApi.Modules.ParkingSlot.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParkingSlotRequestDto {

    @NotNull(message = "Parking location id is mandatory")
    private Long parkingLocationId;

}

package com.practice.RestApi.Modules.ParkingSlot.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingSlotResponseDto {

    private Long id;
    private String slotNumber;
    private String status;
    private Long parkingLocationId;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

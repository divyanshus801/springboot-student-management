package com.practice.RestApi.Modules.ParkingSlot.Mappper;

import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotResponseDto;
import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;


public class ParkingSlotMapper {

    public static ParkingSlotResponseDto toDto(ParkingSlot parkingSlot) {
        ParkingSlotResponseDto dto = new ParkingSlotResponseDto();
        dto.setId(parkingSlot.getId());
        dto.setSlotNumber(parkingSlot.getSlotNumber());
        dto.setStatus(parkingSlot.getStatus().toString());
        dto.setParkingLocationId(parkingSlot.getParkingLocation().getId());
        dto.setOwnerId(parkingSlot.getOwner().getId());
        dto.setCreatedAt(parkingSlot.getCreatedAt());
        dto.setUpdatedAt(parkingSlot.getUpdatedAt());
        return dto;
    }
}

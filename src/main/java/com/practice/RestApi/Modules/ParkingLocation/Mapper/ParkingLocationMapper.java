package com.practice.RestApi.Modules.ParkingLocation.Mapper;

import com.practice.RestApi.Modules.ParkingLocation.Dto.Response.ParkingLocationResponseDto;
import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;

public class ParkingLocationMapper {

    public static ParkingLocationResponseDto mapToDto(ParkingLocation parkingLocation) {
        ParkingLocationResponseDto dto = new ParkingLocationResponseDto();


        dto.setParkingLocationId(parkingLocation.getId());
        dto.setParkingLocationName(parkingLocation.getParkingName());
        dto.setAddress(parkingLocation.getAddress());
        dto.setCity(parkingLocation.getCity());
        dto.setState(parkingLocation.getState());
        dto.setLatitude(parkingLocation.getLatitude().doubleValue());
        dto.setLongitude(parkingLocation.getLongitude().doubleValue());
        dto.setTotalSlots(parkingLocation.getTotalSlots());
        dto.setAvailableSlots(parkingLocation.getAvailableSlots());
        dto.setCreatedAt(parkingLocation.getCreatedAt());
        dto.setUpdatedAt(parkingLocation.getUpdatedAt());

        // Owner info
        dto.setOwnerId(parkingLocation.getOwner().getId());
//        dto.setOwnerName(parkingLocation.getOwner().getName());
//        dto.setOwnerEmail(parkingLocation.getOwner().getEmail());

        return dto;
    }

}

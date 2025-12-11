package com.practice.RestApi.Modules.ParkingLocation.Dto.Response;

import com.practice.RestApi.Modules.User.Entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingLocationResponseDto {

    private Long parkingLocationId;
    private Long ownerId;
    private String parkingLocationName;
    private String state;
    private String city;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double totalSlots;
    private Double availableSlots;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

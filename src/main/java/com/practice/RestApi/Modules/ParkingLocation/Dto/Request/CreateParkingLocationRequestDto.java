package com.practice.RestApi.Modules.ParkingLocation.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateParkingLocationRequestDto {

    @NotBlank(message = "Parking name is mandatory")
    @Size(min = 2,max = 50, message = "At least 2 character and max 50 character is allowed in parking name")
    private String parkingName;

    @NotBlank(message = "City is mandatory")
    @Size(min = 2, max = 50, message = "At least 2 character and max 50 character is allowed in city name")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 5, max = 200, message = "At least 5 character and max 200 character is allowed in address")
    private String address;

    @NotNull(message = "Lattitude is mandatory")
    @DecimalMin(value = "-90.0", message = "Lattitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Lattitude must be between -90 and 90")
    private Float latitude;

    @NotNull(message = "Longitude is mandatory")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Float longitude;

    @NotNull(message = "Total slots is mandatory")
    @Positive(message = "Total slots must be positive number")
    private Double totalSlots;

}

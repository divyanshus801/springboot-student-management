package com.practice.RestApi.Modules.ParkingLocation.Controller;

import com.practice.RestApi.Modules.ParkingLocation.Dto.Request.CreateParkingLocationRequestDto;
import com.practice.RestApi.Modules.ParkingLocation.Dto.Response.ParkingLocationResponseDto;
import com.practice.RestApi.Modules.ParkingLocation.Service.ParkingLocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingLocationController {

    @Autowired
    private ParkingLocationService parkingLocationService;

    @PostMapping("/addParkingLocation")
    public ParkingLocationResponseDto addParkingLocation(@Valid @RequestBody CreateParkingLocationRequestDto createParkingLocationRequestDto) {
        return  parkingLocationService.addParkingLocation(createParkingLocationRequestDto);
    }

}

package com.practice.RestApi.Modules.ParkingLocation.Service;

import com.practice.RestApi.Modules.ParkingLocation.Dto.Request.CreateParkingLocationRequestDto;
import com.practice.RestApi.Modules.ParkingLocation.Dto.Response.ParkingLocationResponseDto;
import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingLocation.Repository.ParkingLocationRepository;
import com.practice.RestApi.Modules.User.Entity.User;
import com.practice.RestApi.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParkingLocationService {

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ParkingLocationResponseDto addParkingLocation(CreateParkingLocationRequestDto createParkingLocationRequestDto) {

        try {
            String parkingName = createParkingLocationRequestDto.getParkingName();
            String address = createParkingLocationRequestDto.getAddress();
            String city = createParkingLocationRequestDto.getCity();
            String state = createParkingLocationRequestDto.getState();
            Float latitude = createParkingLocationRequestDto.getLatitude();
            Float longitude = createParkingLocationRequestDto.getLongitude();
            Double totalSlots = createParkingLocationRequestDto.getTotalSlots();

            Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
            User user = new User();
            user.setId(userId);


            ParkingLocation parkingLocation = new ParkingLocation();
            parkingLocation.setParkingName(parkingName);
            parkingLocation.setAddress(address);
            parkingLocation.setCity(city);
            parkingLocation.setState(state);
            parkingLocation.setLatitude(latitude);
            parkingLocation.setLongitude(longitude);
            parkingLocation.setTotalSlots(totalSlots);
            parkingLocation.setAvailableSlots(totalSlots);
            parkingLocation.setOwner(user);

            ParkingLocation savedLocation = parkingLocationRepository.save(parkingLocation);

            System.out.println((savedLocation));




            return  new ParkingLocationResponseDto();
        } catch (Exception e) {
            System.out.println(e.getMessage());
           throw new RuntimeException("Error while adding parking location: " + e.getMessage());
        }


    }
}

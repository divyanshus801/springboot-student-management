package com.practice.RestApi.Modules.ParkingLocation.Service;

import com.practice.RestApi.Modules.ParkingLocation.Dto.Request.CreateParkingLocationRequestDto;
import com.practice.RestApi.Modules.ParkingLocation.Dto.Response.ParkingLocationResponseDto;
import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingLocation.Mapper.ParkingLocationMapper;
import com.practice.RestApi.Modules.ParkingLocation.Repository.ParkingLocationRepository;
import com.practice.RestApi.Modules.User.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLocationService {

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;


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
            ParkingLocationResponseDto parkingLocationResponseDto = new ParkingLocationResponseDto();
            parkingLocationResponseDto.setParkingLocationId(savedLocation.getId());
            parkingLocationResponseDto.setParkingLocationName(savedLocation.getParkingName());
            parkingLocationResponseDto.setAddress(savedLocation.getAddress());
            parkingLocationResponseDto.setCity(savedLocation.getCity());
            parkingLocationResponseDto.setState(savedLocation.getState());
            parkingLocationResponseDto.setLatitude(Double.valueOf(savedLocation.getLatitude()));
            parkingLocationResponseDto.setLongitude(Double.valueOf(savedLocation.getLongitude()));
            parkingLocationResponseDto.setTotalSlots(savedLocation.getTotalSlots());
            parkingLocationResponseDto.setAvailableSlots(savedLocation.getAvailableSlots());
            parkingLocationResponseDto.setCreatedAt(savedLocation.getCreatedAt());
            parkingLocationResponseDto.setUpdatedAt(savedLocation.getUpdatedAt());
            parkingLocationResponseDto.setOwnerId(savedLocation.getOwner().getId());

            return parkingLocationResponseDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
           throw new RuntimeException("Error while adding parking location: " + e.getMessage());
        }


    }

    public List getAllParkingLocations() {

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        List<ParkingLocation> parkingLocations = parkingLocationRepository.findByOwnerId(userId);


        return parkingLocations.stream().map(ParkingLocationMapper :: mapToDto).toList();

    }
}

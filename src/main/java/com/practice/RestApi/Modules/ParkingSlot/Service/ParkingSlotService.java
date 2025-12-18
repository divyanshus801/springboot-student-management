package com.practice.RestApi.Modules.ParkingSlot.Service;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingLocation.Repository.ParkingLocationRepository;
import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotRequestDto;
import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotResponseDto;
import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;
import com.practice.RestApi.Modules.ParkingSlot.Mappper.ParkingSlotMapper;
import com.practice.RestApi.Modules.ParkingSlot.Repository.ParkingSlotRepository;
import com.practice.RestApi.Modules.User.Entity.User;
import com.practice.RestApi.Utility.ParkingSlotGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;

    @Autowired
    private ParkingSlotGenerator parkingSlotGenerator;

   public ParkingSlotResponseDto addParkingSlot(ParkingSlotRequestDto parkingSlotRequestDto) {

       ParkingLocation location = parkingLocationRepository.findById(parkingSlotRequestDto.getParkingLocationId())
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking Location not found"));

      Long existingSlots =  parkingSlotRepository.countByParkingLocation(location);

       if(existingSlots >= location.getTotalSlots()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Slots are full for this parking location");
       }

       Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

       User user = new User();
       user.setId(userId);

       Long nextSequence = existingSlots+1;
       String slotNumber = parkingSlotGenerator.generate(location, nextSequence);

       ParkingLocation parkingLocation = new ParkingLocation();
       parkingLocation.setId(parkingSlotRequestDto.getParkingLocationId());



       ParkingSlot parkingSlot = new ParkingSlot();
       parkingSlot.setSlotNumber(slotNumber);
       parkingSlot.setParkingLocation(parkingLocation);
       parkingSlot.setOwner(user);

       ParkingSlot savedParkingSlot =   parkingSlotRepository.save(parkingSlot);

       ParkingSlotResponseDto parkingSlotResponseDto = new ParkingSlotResponseDto();
       parkingSlotResponseDto.setId(savedParkingSlot.getId());
       parkingSlotResponseDto.setSlotNumber(savedParkingSlot.getSlotNumber());
       parkingSlotResponseDto.setParkingLocationId(savedParkingSlot.getParkingLocation().getId());
       parkingSlotResponseDto.setStatus(savedParkingSlot.getStatus().toString());
       parkingSlotResponseDto.setOwnerId(savedParkingSlot.getOwner().getId());
       parkingSlotResponseDto.setCreatedAt(parkingSlot.getCreatedAt());
       parkingSlotResponseDto.setUpdatedAt(parkingSlot.getUpdatedAt());
       return parkingSlotResponseDto;


   }

   public List<ParkingSlotResponseDto> getAllParkingSlots() {
       Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

         List<ParkingSlot> parkingSlots =  parkingSlotRepository.findByOwnerId(userId);

        return  parkingSlots.stream().map(ParkingSlotMapper::toDto).toList();


   }

}

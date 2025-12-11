package com.practice.RestApi.Modules.ParkingSlot.Service;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotRequestDto;
import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotResponseDto;
import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;
import com.practice.RestApi.Modules.ParkingSlot.Mappper.ParkingSlotMapper;
import com.practice.RestApi.Modules.ParkingSlot.Repository.ParkingSlotRepository;
import com.practice.RestApi.Modules.User.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

   public ParkingSlotResponseDto addParkingSlot(ParkingSlotRequestDto parkingSlotRequestDto) {

       Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

       User user = new User();
       user.setId(userId);

       ParkingLocation parkingLocation = new ParkingLocation();
         parkingLocation.setId(parkingSlotRequestDto.getParkingLocationId());

       ParkingSlot parkingSlot = new ParkingSlot();
       parkingSlot.setSlotNumber(parkingSlotRequestDto.getSlotNumber());
       parkingSlot.setParkingLocation(parkingLocation);
       parkingSlot.setOwner(user);

       ParkingSlot savedParkingSlot =   parkingSlotRepository.save(parkingSlot);

       System.out.println(savedParkingSlot);

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

         System.out.println(parkingSlots);

        return  parkingSlots.stream().map(ParkingSlotMapper::toDto).toList();


   }

}

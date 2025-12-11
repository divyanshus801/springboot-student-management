package com.practice.RestApi.Modules.ParkingSlot.Controller;

import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotRequestDto;
import com.practice.RestApi.Modules.ParkingSlot.Dto.ParkingSlotResponseDto;
import com.practice.RestApi.Modules.ParkingSlot.Repository.ParkingSlotRepository;
import com.practice.RestApi.Modules.ParkingSlot.Service.ParkingSlotService;
import jakarta.validation.Valid;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @PostMapping("/parkingOwner/addParkingSlot")
    public ParkingSlotResponseDto addParkingSlot(@Valid @RequestBody ParkingSlotRequestDto parkingSlotRequestDto){
          return parkingSlotService.addParkingSlot(parkingSlotRequestDto);
    }

    @GetMapping("/parkingOwner/getParkingSlots")
    public List<ParkingSlotResponseDto> getParkingSlots(){
        return parkingSlotService.getAllParkingSlots();
    }
}

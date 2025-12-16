package com.practice.RestApi.Utility;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import org.springframework.stereotype.Service;

@Service
public class ParkingSlotGenerator {

    public String generate(ParkingLocation location, Long nextSequence){
        return "PARK-"+location.getId()+"-S"+String.format("%03d", nextSequence);
    }

}

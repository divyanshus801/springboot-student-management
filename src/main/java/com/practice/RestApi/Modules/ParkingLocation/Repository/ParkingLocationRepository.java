package com.practice.RestApi.Modules.ParkingLocation.Repository;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation,Long> {

    List<ParkingLocation> findByOwnerId(Long ownerId);



}

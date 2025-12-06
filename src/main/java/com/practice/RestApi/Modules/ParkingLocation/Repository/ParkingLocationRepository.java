package com.practice.RestApi.Modules.ParkingLocation.Repository;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation,Long> {

}

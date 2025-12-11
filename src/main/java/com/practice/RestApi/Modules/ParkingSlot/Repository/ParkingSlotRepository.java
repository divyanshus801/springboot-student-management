package com.practice.RestApi.Modules.ParkingSlot.Repository;

import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    List<ParkingSlot> findByOwnerId(Long parkingLocationId);
}

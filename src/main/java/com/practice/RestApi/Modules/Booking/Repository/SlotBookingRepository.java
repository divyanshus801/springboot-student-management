package com.practice.RestApi.Modules.Booking.Repository;

import com.practice.RestApi.Modules.Booking.Entity.SlotBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SlotBookingRepository extends JpaRepository<SlotBookingEntity, Long> {

    @Query("""
            SELECT b FROM SlotBookingEntity b
            WHERE b.slotId.id = :slotId
            AND b.bookingStatus IN :statuses
            AND b.startTime < :endTime
            AND b.endTime > :startTime
            """)
    List<SlotBookingEntity> findOverlappingBookings(
            @Param("slotId") Long slotId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("statuses") List<SlotBookingEntity.BookingStatus> statuses
    );


}

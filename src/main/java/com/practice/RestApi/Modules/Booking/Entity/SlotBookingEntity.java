package com.practice.RestApi.Modules.Booking.Entity;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;
import com.practice.RestApi.Modules.User.Entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class SlotBookingEntity {

    public  enum BookingStatus  {
        CREATED,
        CONFIRMED ,
        CHECKED_IN ,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_user_id", nullable = false)
    private User bookedUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private ParkingSlot slotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_location_id")
    private ParkingLocation parkingLocationId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private BookingStatus bookingStatus = BookingStatus.CREATED;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

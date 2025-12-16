package com.practice.RestApi.Modules.ParkingSlot.Entity;

import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.User.Entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"slotNumber", "parking_location_id"})
        }
)
public class ParkingSlot {

    public enum ParkingSlotStatus {
        AVAILABLE,
        OCCUPIED,
        RESERVED,
        OUT_OF_SERVICE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingSlotStatus status = ParkingSlotStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_location_id", nullable = false)
    private ParkingLocation parkingLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

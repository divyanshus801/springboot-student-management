package com.practice.RestApi.Modules.Booking.Service;

import com.practice.RestApi.Modules.Booking.Dto.Request.BookingCheckDto;
import com.practice.RestApi.Modules.Booking.Dto.Request.SlotBookingRequestDto;
import com.practice.RestApi.Modules.Booking.Dto.Response.BookingCheckResDto;
import com.practice.RestApi.Modules.Booking.Dto.Response.SlotBookingResponseDto;
import com.practice.RestApi.Modules.Booking.Entity.SlotBookingEntity;
import com.practice.RestApi.Modules.Booking.Repository.SlotBookingRepository;
import com.practice.RestApi.Modules.ParkingLocation.Entity.ParkingLocation;
import com.practice.RestApi.Modules.ParkingLocation.Repository.ParkingLocationRepository;
import com.practice.RestApi.Modules.ParkingSlot.Entity.ParkingSlot;
import com.practice.RestApi.Modules.ParkingSlot.Repository.ParkingSlotRepository;
import com.practice.RestApi.Modules.User.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SlotBookingService {

    @Autowired
    private SlotBookingRepository slotBookingRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ParkingLocationRepository parkingLocationRepository;

    public void validateSlotAvailability(Long slotId, LocalDateTime startTime, LocalDateTime endTime){
        List<SlotBookingEntity.BookingStatus> activeStatuses =
                List.of(
                        SlotBookingEntity.BookingStatus.CONFIRMED,
                        SlotBookingEntity.BookingStatus.CHECKED_IN
                );

        List<SlotBookingEntity> conflicts = slotBookingRepository.findOverlappingBookings(slotId, startTime, endTime, activeStatuses);

        if(!conflicts.isEmpty()){
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot is already booked for the selected time");
        }
    }

    public SlotBookingResponseDto bookSlot(SlotBookingRequestDto slotBookingRequestDto) {

       Long slotId = slotBookingRequestDto.getSlotId();
       LocalDateTime startTime  = slotBookingRequestDto.getStartTime();
       LocalDateTime endTime = slotBookingRequestDto.getEndTime();

       ParkingSlot slot = parkingSlotRepository.findById(slotId)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking Slot Not Found"));

       ParkingLocation  location = slot.getParkingLocation();
       Double PricePerHour =  location.getPricePerHour();


     if(endTime.isBefore(startTime)){
       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time");
     }

     if(startTime.isBefore(LocalDateTime.now())){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking start time cannot be in the past");
     }

      Long minutes =   Duration.between(startTime, endTime).toMinutes();
      Double hours  = minutes/60.0;

     if(minutes < 30){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum booking duration is 30 minutes");
     }

        validateSlotAvailability(slotId, startTime, endTime);

       Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        User user = new User();
        user.setId(userId);

        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setId(slotId);

       Double totalAmount = (hours*PricePerHour);


      SlotBookingEntity bookingSlot = new SlotBookingEntity();
      bookingSlot.setBookedUserId(user);
      bookingSlot.setSlotId(parkingSlot);
      bookingSlot.setStartTime(startTime);
      bookingSlot.setEndTime(endTime);
      bookingSlot.setBookingStatus(SlotBookingEntity.BookingStatus.CONFIRMED);
      bookingSlot.setTotalAmount(totalAmount);
      bookingSlot.setParkingLocationId(location);

      SlotBookingEntity bookedSlot =  slotBookingRepository.save(bookingSlot);

      SlotBookingResponseDto slotBookingResponseDto = new SlotBookingResponseDto();
      slotBookingResponseDto.setId(bookedSlot.getId());
      slotBookingResponseDto.setBookingStatus(bookedSlot.getBookingStatus());
      slotBookingResponseDto.setSlotId(bookedSlot.getSlotId().getId());
      slotBookingResponseDto.setBookedUserId(bookedSlot.getBookedUserId().getId());
      slotBookingResponseDto.setEndTime(bookedSlot.getEndTime());
      slotBookingResponseDto.setStartTime(bookedSlot.getStartTime());
      slotBookingResponseDto.setTotalAmount(bookedSlot.getTotalAmount());
      slotBookingResponseDto.setCreatedAt(bookedSlot.getCreatedAt());
      slotBookingResponseDto.setUpdatedAt(bookedSlot.getUpdatedAt());

       return slotBookingResponseDto;
    }

    @Transactional
    public BookingCheckResDto checkBookedSlot(BookingCheckDto bookingCheckInDto) {
        Long bookingId = bookingCheckInDto.getBookingId();
        String type = bookingCheckInDto.getType();

        SlotBookingEntity bookedSlot = slotBookingRepository.findById(bookingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with id: " + bookingId));

        Long bookedUserId = bookedSlot.getBookedUserId().getId();
        LocalDateTime startTime = bookedSlot.getStartTime();
        LocalDateTime endTime = bookedSlot.getEndTime();
        Long slotId = bookedSlot.getSlotId().getId();

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (!bookedUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to check this booking");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        if ("IN".equals(type)) {
                if ((currentTime.isBefore(startTime) || currentTime.isAfter(endTime))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current time is outside the allowed time window. Valid between " +
                            startTime + " and " + endTime);
                }
                if (bookedSlot.getBookingStatus() == SlotBookingEntity.BookingStatus.CHECKED_IN) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already checked in at " + bookedSlot.getCheckInTime());
                }
               if (bookedSlot.getBookingStatus() != SlotBookingEntity.BookingStatus.CONFIRMED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking must be in CONFIRMED state to check in");
                }
                bookedSlot.setBookingStatus(SlotBookingEntity.BookingStatus.CHECKED_IN);
                bookedSlot.setCheckInTime(currentTime);

                SlotBookingEntity checkedInSlot = slotBookingRepository.save(bookedSlot);

                BookingCheckResDto response = new BookingCheckResDto();
                response.setStatus("success");
                response.setMessage("CheckedIn Success");
                response.setBookingId(checkedInSlot.getId());
                response.setCreatedAt(checkedInSlot.getCreatedAt());
                response.setUpdatedAt(checkedInSlot.getUpdatedAt());
                return response;

        } else if ("OUT".equals(type)) {

            if ((bookedSlot.getBookingStatus() != SlotBookingEntity.BookingStatus.CHECKED_IN) || bookedSlot.getCheckInTime() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot check out. Booking must be checked in first");
            }

            ParkingLocation parkingLocation = parkingLocationRepository.findById(bookedSlot.getParkingLocationId().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Parking Location Found"));

            Double pricePerHour = parkingLocation.getPricePerHour();

            Duration duration = Duration.between(bookedSlot.getCheckInTime(), currentTime);
            Long hours = duration.toHours();
            Long totalMin = duration.toMinutes();

            Double totalHours = totalMin * 60.0;
            Double totalAmountForDuration = pricePerHour * totalHours;

            bookedSlot.setBookingStatus(SlotBookingEntity.BookingStatus.COMPLETED);
            bookedSlot.setCheckOutTime(currentTime);
            bookedSlot.setTotalAmount(totalAmountForDuration);

            SlotBookingEntity checkedInSlot = slotBookingRepository.save(bookedSlot);

            BookingCheckResDto bookingCheckInResponse = new BookingCheckResDto();
            bookingCheckInResponse.setStatus("success");
            bookingCheckInResponse.setMessage("Check Out Successfully");
            bookingCheckInResponse.setType(type);
            bookingCheckInResponse.setBookingId(checkedInSlot.getId());
            bookingCheckInResponse.setCheckInTime(checkedInSlot.getCheckInTime());
            bookingCheckInResponse.setCheckOutTime(checkedInSlot.getCheckOutTime());
            bookingCheckInResponse.setDuration(hours + " hours " + totalMin + " minutes");
            bookingCheckInResponse.setTotalAmount(checkedInSlot.getTotalAmount());
            bookingCheckInResponse.setCreatedAt(checkedInSlot.getCreatedAt());
            bookingCheckInResponse.setUpdatedAt(checkedInSlot.getUpdatedAt());
            return bookingCheckInResponse;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid type. Must be 'IN' or 'OUT'");
        }
    }
}

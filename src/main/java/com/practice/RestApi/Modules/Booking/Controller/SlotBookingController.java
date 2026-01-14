package com.practice.RestApi.Modules.Booking.Controller;

import com.practice.RestApi.Modules.Booking.Dto.Request.BookingCheckDto;
import com.practice.RestApi.Modules.Booking.Dto.Request.SlotBookingRequestDto;
import com.practice.RestApi.Modules.Booking.Dto.Response.BookingCheckResDto;
import com.practice.RestApi.Modules.Booking.Dto.Response.SlotBookingResponseDto;
import com.practice.RestApi.Modules.Booking.Service.SlotBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlotBookingController {

    @Autowired
    private SlotBookingService slotBookingService;

    @PostMapping("/slot/book")
    public SlotBookingResponseDto bookNewSlot(@Valid @RequestBody SlotBookingRequestDto slotBookingRequestDto){
        return slotBookingService.bookSlot(slotBookingRequestDto);
    }

    @PostMapping("/slot/booking/check")
    public BookingCheckResDto checkBookedSlot(@Valid @RequestBody BookingCheckDto bookingCheckInDto){
        return slotBookingService.checkBookedSlot(bookingCheckInDto);
    }

}

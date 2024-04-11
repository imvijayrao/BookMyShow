package com.bookmyshow.demo.controllers;

import com.bookmyshow.demo.dtos.BookShowRequestDto;
import com.bookmyshow.demo.dtos.BookShowResponseDto;
import com.bookmyshow.demo.dtos.ResponseStatus;
import com.bookmyshow.demo.exceptions.SeatNotAvailable;
import com.bookmyshow.demo.exceptions.ShowNotFound;
import com.bookmyshow.demo.exceptions.UserIsNotValid;
import com.bookmyshow.demo.models.Booking;
import com.bookmyshow.demo.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private BookingService bookingService;
    private static final String User_Invalid = "User ID is Invalid";
    private static final String Show_Invalid = "Show is Invalid";

    private static final String SomethingWentWrong = "Something Went Wrong";

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookShowResponseDto bookShow(BookShowRequestDto requestDto) {


        try {
            Booking booking =  bookingService.bookShow(requestDto);
            return new BookShowResponseDto(booking.getId(), booking.getAmount(), ResponseStatus.SUCCESS, "Successfully Completed");
        } catch (UserIsNotValid e) {
            return new BookShowResponseDto(null, 0, ResponseStatus.FAILURE, User_Invalid);
        } catch (ShowNotFound e) {
            return new BookShowResponseDto(null, 0, ResponseStatus.FAILURE, Show_Invalid);
        } catch (SeatNotAvailable e) {
            return new BookShowResponseDto(null, 0, ResponseStatus.FAILURE, SomethingWentWrong);

        }
    }
}

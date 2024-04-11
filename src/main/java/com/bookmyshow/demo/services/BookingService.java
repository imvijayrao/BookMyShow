package com.bookmyshow.demo.services;

import com.bookmyshow.demo.dtos.BookShowRequestDto;
import com.bookmyshow.demo.exceptions.SeatNotAvailable;
import com.bookmyshow.demo.exceptions.ShowNotFound;
import com.bookmyshow.demo.exceptions.UserIsNotValid;
import com.bookmyshow.demo.models.*;
import com.bookmyshow.demo.repository.BookingRepository;
import com.bookmyshow.demo.repository.ShowRespository;
import com.bookmyshow.demo.repository.ShowSeatRepository;
import com.bookmyshow.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRespository showRespository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(UserRepository userRepository, ShowRespository showRespository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRespository = showRespository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
    }

    public Booking bookShow(BookShowRequestDto requestDto) throws UserIsNotValid, ShowNotFound, SeatNotAvailable {

        //Get User with userid
        Optional<User> user = userRepository.findById(requestDto.getUserId());
        if (user.isEmpty()) {
            throw new UserIsNotValid();
        }

        //Get show with show id
        Optional<Show> show = showRespository.findById(requestDto.getShowId());
        if (show.isEmpty()) {
            throw new ShowNotFound();
        }

        List<ShowSeat> reserveShowSeats = reserveShowSeats(requestDto.getShowSeatId());

        return reserveBooking(requestDto, user, reserveShowSeats);
    }

    private Booking reserveBooking(BookShowRequestDto requestDto, Optional<User> user, List<ShowSeat> reserveShowSeats) {
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculator(requestDto.getShowSeatId(), requestDto.getShowId()));
        booking.setUser(user.get());
        booking.setShowSeatList(reserveShowSeats);
        booking.setPayments(new ArrayList<>());

        return bookingRepository.save(booking);
    }

    //Price calculator can be a separate service instead of using same service
    private int priceCalculator(List<Long> showSeatId, Long ShowId) {

        //get the shows
        //get the seats
        //with seat ids you can find seat type
        //for pair(showid, seattype) -> price
        //sum it for all seats together

        return 0;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> reserveShowSeats(List<Long> showSeatId) throws SeatNotAvailable {

        //get List<showseat> for showseat Ids
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatId);

        for(ShowSeat showSeat: showSeats){
            seatAvailableOrNot(showSeat);
        }

        //Locking will be done only if (seats are available || seats having lockedDuration > 10).
        List<ShowSeat> reservedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat: reservedShowSeats){
            showSeat.setStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            reservedShowSeats.add(showSeatRepository.save(showSeat));
        }

        return reservedShowSeats;
    }

    private static boolean seatAvailableOrNot(ShowSeat showSeat) throws SeatNotAvailable {
        if(!ShowSeatStatus.AVAILABLE.equals(showSeat.getStatus())){
            long lockedDuration = Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes();
            if (ShowSeatStatus.BOOKED.equals(showSeat.getStatus())) {
                throw new SeatNotAvailable();
            }
            // duration time is configurable
            if((ShowSeatStatus.LOCKED.equals(showSeat.getStatus()) && lockedDuration < 10)){
                throw new SeatNotAvailable();
            }
        }
        return true;
    }
}

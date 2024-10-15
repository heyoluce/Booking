package kg.epam.booking.service;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.domain.entities.user.User;
import kg.epam.booking.domain.exception.AccessDeniedException;
import kg.epam.booking.domain.exception.ResourceNotFoundException;
import kg.epam.booking.domain.exception.RoomNotAvailableException;
import kg.epam.booking.repository.BookingRepository;
import kg.epam.booking.repository.HotelRepository;
import kg.epam.booking.repository.UserRepository;
import kg.epam.booking.web.dto.BookingDto;
import kg.epam.booking.web.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookingMapper bookingMapper;

    public Booking createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);
        booking.setHotel(hotelRepository.findById(bookingDto.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found")));
        booking.setUser(userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));


        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> getUserBookings(Pageable pageable) {
        User user = (User) userService.getCurrentUser();
        return bookingRepository.getAllByUserId(user.getId(), pageable);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        updatedBooking.setId(id);

        if (!isRoomAvailable(updatedBooking.getHotel(), updatedBooking.getCheckInDate(), updatedBooking.getCheckOutDate())) {
            throw new RoomNotAvailableException();
        }

        return bookingRepository.save(updatedBooking);
    }

    public void deleteBooking(Long id) {
        User user = (User) userService.getCurrentUser();
        Booking booking = getBookingById(id);
        if (booking.getUser().getId().equals(user.getId())) {
            bookingRepository.delete(booking);
        } else {
            throw new AccessDeniedException();
        }
    }

    private boolean isRoomAvailable(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate) {
        int bookedRooms = bookingRepository.countBookingsByHotelAndDates(hotel, checkInDate, checkOutDate);
        return (hotel.getTotalRooms() - bookedRooms) > 0;
    }

}

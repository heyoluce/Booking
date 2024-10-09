package kg.epam.booking.service;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.domain.entities.user.User;
import kg.epam.booking.domain.exception.ResourceNotFoundException;
import kg.epam.booking.domain.exception.RoomNotAvailableException;
import kg.epam.booking.repository.BookingRepository;
import kg.epam.booking.repository.HotelRepository;
import kg.epam.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public Booking createBooking(Booking booking) {
        Hotel hotel = hotelRepository.findById(booking.getHotel().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!isRoomAvailable(hotel, booking.getCheckInDate(), booking.getCheckOutDate())) {
            throw new RoomNotAvailableException();
        }

        booking.setHotel(hotel);
        booking.setUser(user);

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
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
        Booking existingBooking = getBookingById(id);
        bookingRepository.delete(existingBooking);
    }

    private boolean isRoomAvailable(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate) {
        int bookedRooms = bookingRepository.countBookingsByHotelAndDates(hotel, checkInDate, checkOutDate);
        return (hotel.getTotalRooms() - bookedRooms) > 0;
    }
}

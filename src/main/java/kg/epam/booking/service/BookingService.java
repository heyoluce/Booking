package kg.epam.booking.service;

import jakarta.transaction.Transactional;
import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.domain.entities.Hotel;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    public Booking createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);

        Hotel hotel = hotelRepository.findById(bookingDto.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        booking.setHotel(hotel);
        booking.setUser(userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        BigDecimal pricePerNight = hotel.getPricePerNight();
        long daysBetween = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
        BigDecimal totalPrice = pricePerNight.multiply(BigDecimal.valueOf(daysBetween));
        booking.setTotalPrice(totalPrice);
        booking.setActive(true);
        booking.setCreatedAt(Instant.now());
        if (isRoomAvailable(hotel, booking.getCheckInDate(), booking.getCheckOutDate())) {
            throw new RoomNotAvailableException();
        }

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> getUserBookings(Long userId, Pageable pageable) {
        return bookingRepository.getByUserIdAndActiveIsTrue(userId, pageable);
    }

    @Transactional
    public Booking updateBooking(Long id, Booking updatedBooking) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        updatedBooking.setId(id);

        if (isRoomAvailable(updatedBooking.getHotel(), updatedBooking.getCheckInDate(), updatedBooking.getCheckOutDate())) {
            throw new RoomNotAvailableException();
        }

        return bookingRepository.save(updatedBooking);
    }

    @Transactional
    public void cancelBooking(Long userId, Long id) {
        Booking booking = getBookingById(id);
        if (userId.equals(booking.getUser().getId())) {
            booking.setActive(false);
            bookingRepository.save(booking);
        }
        else {
            throw new AccessDeniedException();
        }
    }

    private boolean isRoomAvailable(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate) {
        int bookedRooms = bookingRepository.countBookingsByHotelAndDates(hotel, checkInDate, checkOutDate);
        return (hotel.getTotalRooms() - bookedRooms) <= 0;
    }

}

package kg.epam.booking.service;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.repository.BookingRepository;
import kg.epam.booking.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    public BookingService(BookingRepository bookingRepository, HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
    }

    public Booking createBooking(Long hotelId, String customerName, String customerEmail, LocalDate checkIn, LocalDate checkOut) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setCustomerName(customerName);
        booking.setCustomerEmail(customerEmail);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);

        return bookingRepository.save(booking);
    }
}

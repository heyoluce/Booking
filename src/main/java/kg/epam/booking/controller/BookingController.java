package kg.epam.booking.controller;



import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestParam Long hotelId,
                                 @RequestParam String customerName,
                                 @RequestParam String customerEmail,
                                 @RequestParam String checkIn,
                                 @RequestParam String checkOut) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        return bookingService.createBooking(hotelId, customerName, customerEmail, checkInDate, checkOutDate);
    }
}

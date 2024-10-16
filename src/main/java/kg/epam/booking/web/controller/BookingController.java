package kg.epam.booking.web.controller;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.service.BookingService;
import kg.epam.booking.web.dto.BookingDto;
import kg.epam.booking.web.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        Booking createdBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingMapper.toDto(createdBooking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(bookingMapper.toDto(booking));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BookingDto>> getAllBookings(Pageable pageable) {
        Page<Booking> bookings = bookingService.getAllBookings(pageable);
        List<BookingDto> bookingDtos = bookingMapper.toDto(bookings.getContent());
        Page<BookingDto> bookingDtoPage = new PageImpl<>(bookingDtos, pageable, bookings.getTotalElements());
        return ResponseEntity.ok(bookingDtoPage);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BookingDto>> getUserBookings(@PathVariable Long userId, Pageable pageable) {
        Page<Booking> bookings = bookingService.getUserBookings(userId, pageable);
        List<BookingDto> bookingDtos = bookingMapper.toDto(bookings.getContent());
        Page<BookingDto> bookingDtoPage = new PageImpl<>(bookingDtos, pageable, bookings.getTotalElements());

        return ResponseEntity.ok(bookingDtoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto updatedBookingDto) {
        Booking updatedBooking = bookingMapper.toEntity(updatedBookingDto);
        Booking booking = bookingService.updateBooking(id, updatedBooking);
        return ResponseEntity.ok(bookingMapper.toDto(booking));
    }

    @PatchMapping("/cancel/{userId}/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long userId, @PathVariable Long id) {
        bookingService.cancelBooking(userId, id);
        return ResponseEntity.noContent().build();
    }
}

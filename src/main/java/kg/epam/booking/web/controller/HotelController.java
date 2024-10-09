package kg.epam.booking.web.controller;


import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public Page<Hotel> getAllHotels(@PageableDefault(size = 10) Pageable pageable) {
        return hotelService.getAll(pageable);
    }

    @GetMapping("/city/{city}")
    public Page<Hotel> getHotelsByCity(
            @PathVariable String city,
            @PageableDefault(size = 10) Pageable pageable) {
        return hotelService.search(city, pageable);
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.getById(id);
    }
}

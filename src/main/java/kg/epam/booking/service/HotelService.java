package kg.epam.booking.service;


import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.domain.exception.ResourceNotFoundException;
import kg.epam.booking.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> searchHotels(String city) {
        return hotelRepository.findByCity(city);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
}

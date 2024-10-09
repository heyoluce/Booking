package kg.epam.booking.service;


import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.domain.exception.ResourceNotFoundException;
import kg.epam.booking.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Page<Hotel> search(String city, Pageable pageable) {
        return hotelRepository.findByCity(city, pageable);
    }

    public Hotel getById(Long id) {
        return hotelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Page<Hotel> getAll(Pageable pageable) {
        return hotelRepository.getAll(pageable);
    }

    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel update(Long id, Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void delete(Long id) {
        hotelRepository.deleteById(id);
    }

}

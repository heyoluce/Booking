package kg.epam.booking.web.controller;

import kg.epam.booking.service.HotelService;
import kg.epam.booking.web.dto.HotelDto;
import kg.epam.booking.web.mappers.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<Page<HotelDto>> getAllHotels(@PageableDefault(size = 10) Pageable pageable) {
        Page<HotelDto> hotelDtos = hotelService.getAll(pageable).map(hotelMapper::toDto);
        return ResponseEntity.ok(hotelDtos);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<Page<HotelDto>> getHotelsByCity(
            @PathVariable String city,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<HotelDto> hotelDtos = hotelService.search(city, pageable).map(hotelMapper::toDto);
        return ResponseEntity.ok(hotelDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelMapper.toDto(hotelService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        HotelDto createdHotel = hotelMapper.toDto(hotelService.create(hotelMapper.toEntity(hotelDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        HotelDto updatedHotel = hotelMapper.toDto(hotelService.update(id, hotelMapper.toEntity(hotelDto)));
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

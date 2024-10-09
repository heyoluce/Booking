package kg.epam.booking.repository;

import kg.epam.booking.domain.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Page<Hotel> findByCity(String city, Pageable pageable);

    Page<Hotel> getAll(Pageable pageable);
}

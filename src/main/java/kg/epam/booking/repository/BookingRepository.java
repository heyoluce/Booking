package kg.epam.booking.repository;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.domain.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> getAll(Pageable pageable);

    Page<Booking> getBookingByHotelId(Long id, Pageable pageable);

    Page<Booking> getAllByUserId(Long id, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.hotel = :hotel AND "
            + "(b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)")
    int countBookingsByHotelAndDates(@Param("hotel") Hotel hotel,
                                     @Param("checkInDate") LocalDate checkInDate,
                                     @Param("checkOutDate") LocalDate checkOutDate);

}

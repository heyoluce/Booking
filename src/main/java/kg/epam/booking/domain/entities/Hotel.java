package kg.epam.booking.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String description;

    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;

    private double rating;

    @Column(name = "total_rooms")
    private int totalRooms;

    @Column(name = "booked_rooms")
    private int bookedRooms;

}
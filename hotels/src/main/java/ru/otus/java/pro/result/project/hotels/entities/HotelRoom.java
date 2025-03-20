package ru.otus.java.pro.result.project.hotels.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@ToString(exclude = {"userOrders", "hotelRoomRates"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_rooms")
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_rooms_id_gen")
    @SequenceGenerator(name = "hotel_rooms_id_gen", sequenceName = "hotel_rooms_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "size")
    private Short size;

    @ColumnDefault("1")
    @Column(name = "inside_rooms_number")
    private Short insideRoomsNumber;

    @ColumnDefault("1")
    @Column(name = "total_in_hotel_number")
    private Short totalInHotelNumber;

    @Column(name = "available_count")
    private Short availableCount;

    @Column(name = "max_guests")
    private Short maxGuests;

    @OneToMany(mappedBy = HotelRoomBed_.HOTEL_ROOM)
    private Set<HotelRoomBed> hotelRoomBeds = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "hotel_rooms_hotel_room_amenities_rel",
            joinColumns = @JoinColumn (name = "hotel_room_id"),
    inverseJoinColumns = @JoinColumn(name = "hotel_room_amenity_id"))
    private Set<HotelRoomAmenity> hotelRoomAmenities = new LinkedHashSet<>();

    @OneToMany(mappedBy = HotelRoomRate_.HOTEL_ROOM)
    private Set<HotelRoomRate> hotelRoomRates = new LinkedHashSet<>();

    @OneToMany(mappedBy = UserOrder_.HOTEL_ROOM)
    private Set<UserOrder> userOrders = new LinkedHashSet<>();

}
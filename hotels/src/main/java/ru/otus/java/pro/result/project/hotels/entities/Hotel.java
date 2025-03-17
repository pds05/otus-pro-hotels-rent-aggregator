package ru.otus.java.pro.result.project.hotels.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedEntityGraph(name = "Graph.Hotel.Detailed",
        attributeNodes = {
                @NamedAttributeNode(Hotel_.HOTEL_TYPE),
                @NamedAttributeNode(Hotel_.CITY),
                @NamedAttributeNode(value = Hotel_.HOTELS_HOTEL_AMENITIES,
                        subgraph = "Graph.Hotel.HotelAmenities.hotelAmenity"),
                @NamedAttributeNode(value = Hotel_.HOTEL_ROOMS,
                        subgraph = "Graph.Hotel.HotelRoom.Detailed")
        },
        subgraphs = {
                @NamedSubgraph(name = "Graph.Hotel.HotelAmenities.hotelAmenity",
                        attributeNodes = @NamedAttributeNode(value = HotelsHotelAmenity_.HOTEL_AMENITY,
                                subgraph = "Graph.Hotel.HotelAmenity.hotelAmenityGroup")),
                @NamedSubgraph(name = "Graph.Hotel.HotelAmenity.hotelAmenityGroup",
                        attributeNodes = @NamedAttributeNode(HotelAmenity_.HOTEL_AMENITY_GROUP)),
                @NamedSubgraph(name = "Graph.Hotel.HotelRoom.Detailed",
                        attributeNodes = {
                                @NamedAttributeNode(value = HotelRoom_.HOTEL_ROOM_AMENITIES),
                                @NamedAttributeNode(value = HotelRoom_.HOTEL_ROOM_BEDS, subgraph = "Graph.Hotel.HotelRoom.HotelRoomBed.bedType"),
                                @NamedAttributeNode(value = HotelRoom_.HOTEL_ROOMS_RATES, subgraph = "Graph.Hotel.HotelRoom.HotelRoomsRate.feedType")

                        }),
                @NamedSubgraph(name = "Graph.Hotel.HotelRoom.HotelRoomBed.bedType",
                        attributeNodes = @NamedAttributeNode(HotelRoomBed_.BED_TYPE)),
                @NamedSubgraph(name = "Graph.Hotel.HotelRoom.HotelRoomsRate.feedType",
                        attributeNodes = @NamedAttributeNode(HotelRoomsRate_.FEED_TYPE))
        })

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotels_id_gen")
    @SequenceGenerator(name = "hotels_id_gen", sequenceName = "hotels_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "hotel_type_id")
    private CtHotelType hotelType;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "city_id", nullable = false)
    private CtCity city;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "building_year", length = 4)
    private String buildingYear;

    @Column(name = "rooms_value")
    private Short roomsValue;

    @Column(name = "user_rating", precision = 3, scale = 1)
    private BigDecimal userRating;

    @Column(name = "location_desc", length = Integer.MAX_VALUE)
    private String locationDesc;

    @Column(name = "hotel_desc", length = Integer.MAX_VALUE)
    private String hotelDesc;

    @Column(name = "rooms_desc", length = Integer.MAX_VALUE)
    private String roomsDesc;

    @Column(name = "guest_info", length = Integer.MAX_VALUE)
    private String guestInfo;

    @Column(name = "time_check_in")
    private LocalTime timeCheckIn;

    @Column(name = "time_check_out")
    private LocalTime timeCheckOut;

    @Column(name = "is_active")
    private Boolean isActive;

    @ColumnDefault("0")
    @Column(name = "star_grade")
    private Short starGrade;

    @OneToMany(mappedBy = HotelRoom_.HOTEL)
    private Set<HotelRoom> hotelRooms = new LinkedHashSet<>();

    public void addHotelRooms(HotelRoom hotelRoom) {
        hotelRooms.add(hotelRoom);
    }

    public void removeHotelRooms(HotelRoom hotelRoom) {
        hotelRooms.removeIf(room -> room.getId().equals(hotelRoom.getId()));
    }

    @OneToMany(mappedBy = HotelsHotelAmenity_.HOTEL)
    private Set<HotelsHotelAmenity> hotelsHotelAmenities = new LinkedHashSet<>();

    public void addHotelAmenities(HotelsHotelAmenity hotelAmenity) {
        hotelsHotelAmenities.add(hotelAmenity);
    }

    public void removeHotelAmenities(HotelsHotelAmenity hotelAmenity) {
        hotelsHotelAmenities.removeIf(amenity -> amenity.getPrimaryKey().equals(hotelAmenity.getPrimaryKey()));
    }
}
package ru.vadim.hostel.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer countOfRooms;
    @OneToMany(mappedBy = "apartment")
    private List<Guest> guests;
    private LocalDate dateOfCleaning;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Category category;
}

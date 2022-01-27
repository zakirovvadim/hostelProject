package ru.vadim.hostel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer countOfRooms;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "apartment")
    @JsonBackReference
    private List<Guest> guests;
    private LocalDate dateOfCleaning;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Category category;
}

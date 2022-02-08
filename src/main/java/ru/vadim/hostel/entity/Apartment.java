package ru.vadim.hostel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@Data
@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer countOfRooms;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, MERGE}, mappedBy = "apartment")
    @JsonBackReference
    private List<Guest> guests;
    private LocalDate dateOfCleaning;
    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;
}

package ru.vadim.hostel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String passport;
    @Lob
    private Byte [] image;
    private LocalDate birthdate;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    @JsonManagedReference
    private Apartment apartment;
}

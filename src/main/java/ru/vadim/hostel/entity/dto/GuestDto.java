package ru.vadim.hostel.entity.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.vadim.hostel.entity.Apartment;

import java.time.LocalDate;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
public class GuestDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Long passport;
    private Byte[] image;
    private LocalDate birthdate;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnore
    private ApartmentDto apartment;


}

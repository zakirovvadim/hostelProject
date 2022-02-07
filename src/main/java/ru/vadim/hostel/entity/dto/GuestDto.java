package ru.vadim.hostel.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GuestDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String passport;
    private byte[] image;
    private LocalDate birthdate;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonIgnore
    private ApartmentDto apartment;


}

package ru.vadim.hostel.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vadim.hostel.entity.Apartment;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GuestDto {
    private String firstname;
    private String lastname;
    private String patronymic;
    private Integer passport;
    private Byte[] image;
    private LocalDate birthdate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Apartment apartment;
}

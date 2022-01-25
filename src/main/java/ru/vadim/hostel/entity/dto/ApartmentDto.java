package ru.vadim.hostel.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.Guest;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ApartmentDto {
    private Long number;
    private Integer countOfRooms;
    private List<Guest> guests;
    private LocalDate dateOfCleaning;
    private Category category;
}

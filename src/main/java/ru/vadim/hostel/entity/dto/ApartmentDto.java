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
    private Long id;
    private Long number;
    private Integer countOfRooms;
    private List<GuestDto> guests;
    private LocalDate dateOfCleaning;
    private CategoryDto category;
}

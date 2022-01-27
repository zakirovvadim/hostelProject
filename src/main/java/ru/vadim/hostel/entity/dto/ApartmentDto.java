package ru.vadim.hostel.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApartmentDto {
    private Long id;
    private Long number;
    private Integer countOfRooms;
    @JsonIgnore
    private List<GuestDto> guests;
    private LocalDate dateOfCleaning;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CategoryDto category;

    @JsonIgnore
    public CategoryDto getCategory() {
        return category;
    }
}

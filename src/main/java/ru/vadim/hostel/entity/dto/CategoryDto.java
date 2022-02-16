package ru.vadim.hostel.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto implements Serializable {
    private Long id;
    private String name;
    private String description;
}

package ru.vadim.hostel.mapper;

import org.mapstruct.Mapper;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.CategoryDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> map(List<Category> categories);

    CategoryDto map(Category category);

    Category map(CategoryDto dto);
}

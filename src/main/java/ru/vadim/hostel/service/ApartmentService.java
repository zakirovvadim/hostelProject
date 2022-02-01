package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.repository.ApartmentRepository;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository repository;
    private final ApartmentMapper mapper;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    //myself notice - if apartment's category already have had, we replace this category on category from database
    public ApartmentDto save(ApartmentDto dto) {
        if (dto.getCategory() != null) {
            CategoryDto foundCategory = categoryService.findByName(dto.getCategory().getName());
            if (foundCategory.getName() != null) {
                dto.setCategory(foundCategory);
                Apartment apartment = mapper.map(dto);
                return mapper.map(repository.save(apartment));
            } else {
                Apartment apartment = mapper.map(dto);
                return mapper.map(repository.save(apartment));
            }
        } else {
            Apartment apartment = mapper.map(dto);
            return mapper.map(repository.save(apartment));
        }
    }

    public void delete(Long number) {
        Apartment apartment = repository.findApartmentByNumber(number).orElseThrow(() -> new NoEntityException(number));
        repository.deleteById(apartment.getId());
    }

    public ApartmentDto getApartmentByNumber(Long number) {
        Apartment apartment = repository.findApartmentByNumber(number).orElseThrow(() -> new NoEntityException(number));
        return mapper.map(apartment);
    }

    public ApartmentDto appointCategoryToApartment(Long number, Long categoryId) {
        Apartment apartment = mapper.map(getApartmentByNumber(number));
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        apartment.setCategory(categoryMapper.map(categoryDto));
        return mapper.map(repository.save(apartment));
    }
}
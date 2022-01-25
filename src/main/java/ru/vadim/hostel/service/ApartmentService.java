package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.repository.ApartmentRepository;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository repository;
    private final ApartmentMapper mapper;

    public ApartmentDto save(ApartmentDto dto) {
        Apartment apartment = mapper.map(dto);
        return mapper.map(repository.save(apartment));
    }

    public boolean delete(Long number) {
        Apartment apartment = repository.findApartmentByNumber(number).orElseThrow(() -> new NoEntityException(number));
        repository.deleteById(apartment.getId());
        return true;
    }

    public ApartmentDto getApartmentByNumber(Long number) {
        Apartment apartment = repository.findApartmentByNumber(number).orElseThrow(() -> new NoEntityException(number));
        ApartmentDto dto = mapper.map(apartment);
        return dto;
    }
    
}

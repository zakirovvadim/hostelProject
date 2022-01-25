package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.repository.ApartmentRepository;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository repository;
    private final ApartmentMapper mapper;

    public Apartment save(ApartmentDto dto) {
        Apartment apartment = mapper.map(dto);
        return repository.save(apartment);
    }
}

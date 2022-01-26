package ru.vadim.hostel.mapper;

import org.mapstruct.Mapper;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.dto.ApartmentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
    List<ApartmentDto> map(List<Apartment> apartments);

    ApartmentDto map(Apartment apartment);

    Apartment map(ApartmentDto dto);
}

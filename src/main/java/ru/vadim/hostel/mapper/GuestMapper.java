package ru.vadim.hostel.mapper;

import org.mapstruct.Mapper;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.GuestDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    List<GuestDto> map(List<Guest> guests);

    GuestDto map(Guest guest);

    Guest map(GuestDto dto);
}

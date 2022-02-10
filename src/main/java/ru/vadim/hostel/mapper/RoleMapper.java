package ru.vadim.hostel.mapper;

import org.mapstruct.Mapper;
import ru.vadim.hostel.entity.Role;
import ru.vadim.hostel.entity.dto.RoleDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    List<RoleDto> map(List<Role> roles);
    RoleDto map(Role role);
    Role map(RoleDto dto);
}

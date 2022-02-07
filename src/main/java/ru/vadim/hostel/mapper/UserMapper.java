package ru.vadim.hostel.mapper;

import org.mapstruct.Mapper;
import ru.vadim.hostel.entity.User;
import ru.vadim.hostel.entity.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDto> map(List<User> users);
    UserDto map(User user);
    User map(UserDto dto);
}

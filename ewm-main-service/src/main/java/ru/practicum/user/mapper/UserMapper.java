package ru.practicum.user.mapper;

import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;


public class UserMapper {
    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User dtoToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }

}

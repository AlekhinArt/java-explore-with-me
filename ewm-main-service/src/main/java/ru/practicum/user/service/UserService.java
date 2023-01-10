package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserService {
    UserDto createUser(User user);

    void deleteUser(long id);

    Collection<UserDto> getAllUsers(Set<Long> ids, Integer from, Integer size);

    User getById(long userId);
}

package ru.practicum.user.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserService {
    UserDto createUser(User user);

    void deleteUser(long id);

    Collection<UserDto> getAllUsers(Set<Integer> ids, Integer from, Integer size);
}

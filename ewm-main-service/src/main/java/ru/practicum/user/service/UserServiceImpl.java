package ru.practicum.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exceptions.AnybodyUseEmailOrNameException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto createUser(User user) {
        User newUser;
        try {
            newUser = userRepository.save(user);
        } catch (Exception e) {
            throw new AnybodyUseEmailOrNameException("имя или email");
        }
        log.info("createUser user: {}", user);
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(long id) {
        try {
            log.info("deleteUser id: {}", id);
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.debug("deleteUser.NotFoundException(Пользователь не найден)");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @Override
    public Collection<UserDto> getAllUsers(Set<Integer> ids, Integer from, Integer size) {
        log.info("Get all users with param ids: {}, from: {}, size: {};",ids,from,size );
        Pageable pageable = PageRequest.of(from/size, size);
       return userRepository.findAllById(ids, pageable).stream()
               .map(UserMapper::toUserDto)
               .collect(Collectors.toList());

    }
}

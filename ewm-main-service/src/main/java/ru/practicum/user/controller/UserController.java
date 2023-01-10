package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        log.info("createUser user : {}", user);
        return userService.createUser(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("deleteUser userId: {}", id);
        userService.deleteUser(id);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers(@RequestParam(name = "ids", required = false) Set<Long> ids,
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("getAllUsers with param ids: {}, from: {}, size: {}", ids, from, size);
        return userService.getAllUsers(ids, from, size);
    }


}

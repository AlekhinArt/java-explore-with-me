package ru.practicum.adminApi.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ru.practicum.adminApi.user.dto.UserDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@Validated @RequestBody UserDto user) {
        log.info("createUser user : {}", user);
        return userClient.createUser(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PositiveOrZero @PathVariable long id) {
        log.info("deleteUser userId: {}", id);
       return userClient.deleteUser(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "", required = false) Set<String> ids,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("getAllUsers with param ids: {}, from: {}, size: {}", ids, from, size);
        return userClient.getAllUsers(ids, from, size);
    }


}

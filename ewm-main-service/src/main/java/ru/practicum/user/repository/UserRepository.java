package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.model.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Page <User> findAllById(Set<Integer> ids, Pageable pageable);
}

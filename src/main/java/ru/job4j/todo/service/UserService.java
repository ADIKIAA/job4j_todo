package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteById(int id);

    boolean update(int id, User user);

}

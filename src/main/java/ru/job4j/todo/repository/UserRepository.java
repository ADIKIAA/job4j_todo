package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteById(int id);

    boolean update(int id, User user);

}

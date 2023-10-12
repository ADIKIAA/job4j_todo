package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    boolean delete(int id);

    boolean update(int id, Task task);

    boolean done(int id);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findByBoolean(boolean done);

}

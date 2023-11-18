package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query(
                "from Priority",
                Priority.class
        );
    }

    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.optional(
                "from Priority WHERE id = :id",
                Priority.class,
                Map.of("id", id));
    }

}

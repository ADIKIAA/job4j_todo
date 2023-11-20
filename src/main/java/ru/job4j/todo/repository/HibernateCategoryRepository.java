package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query(
                "from Category",
                Category.class
        );
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "from Category WHERE id = :id",
                Category.class,
                Map.of("id", id));
    }

}

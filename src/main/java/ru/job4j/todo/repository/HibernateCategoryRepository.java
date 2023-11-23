package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.*;
import java.util.stream.Collectors;

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
    public Set<Category> findByIds(Set<Integer> ids) {
        return new HashSet<>(crudRepository.query(
                "from Category WHERE id in :ids",
                Category.class,
                Map.of("ids", ids)));
    }

}
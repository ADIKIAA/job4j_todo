package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository {

    Collection<Category> findAll();

    Set<Category> findByIds(Set<Integer> ids);

}

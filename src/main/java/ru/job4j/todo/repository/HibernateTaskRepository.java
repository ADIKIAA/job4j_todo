package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.execute(
                "DELETE from Task WHERE id = :id",
                Map.of("id", id)
        ) > 0;
    }

    @Override
    public boolean update(int id, Task task) {
        return crudRepository.execute(
            """
               UPDATE Task SET title = :title, description = :description, done = :done
               WHERE id = :id
               """,
                Map.of("title", task.getTitle(),
                        "description", task.getDescription(),
                        "done", task.isDone(),
                        "id", id)
        ) > 0;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "from Task WHERE id = :id",
                Task.class,
                Map.of("id", id));
    }

    @Override
    public Collection<Task> findAll() {
       return crudRepository.query(
                "from Task",
                Task.class);
    }

    @Override
    public Collection<Task> findNew() {
        return crudRepository.query(
                "from Task WHERE done = :done",
                Task.class,
                Map.of("done", false));
    }

    @Override
    public Collection<Task> findDone() {
        return crudRepository.query(
                "from Task WHERE done = :done",
                Task.class,
                Map.of("done", true));
    }

}

package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery("DELETE Task WHERE id = :id")
                    .setParameter("id", id);
            rsl = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean update(int id, Task task) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery(
                """
                   UPDATE Task SET title = :title, description = :description, done = :done
                   WHERE id = :id
                   """)
                    .setParameter("id", id)
                    .setParameter("title", task.getTitle())
                    .setParameter("description", task.getDescription())
                    .setParameter("done", task.isDone());
            rsl = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> rsl;
        try {
            rsl = session.createQuery("from Task WHERE id = :id", Task.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        Collection<Task> rsl;
        try {
            rsl = session.createQuery("from Task", Task.class).list();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findNew() {
        Session session = sf.openSession();
        Collection<Task> rsl;
        try {
            rsl = session.createQuery("from Task WHERE done = false", Task.class).list();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findDone() {
        Session session = sf.openSession();
        Collection<Task> rsl;
        try {
            rsl = session.createQuery("from Task WHERE done = true", Task.class).list();
        } finally {
            session.close();
        }
        return rsl;
    }

}

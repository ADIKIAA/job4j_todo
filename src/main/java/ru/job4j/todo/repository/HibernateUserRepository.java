package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    @Override
    public User save(User user) {
        crudRepository.run((Consumer<Session>) session -> session.persist(user));
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return crudRepository.query("from User", User.class);
    }

    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "from User WHERE id = :id",
                User.class,
                Map.of("id", id));
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return crudRepository.optional(
                "from User WHERE login = :email AND password = :password",
                User.class,
                Map.of("email", email,
                        "password", password));
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.execute(
                "DELETE from User WHERE id = :id",
                Map.of("id", id)
        ) > 0;
    }

    @Override
    public boolean update(int id, User user) {
        return crudRepository.execute(
                """
                        UPDATE User SET name = :name, login = :login, password = :password
                        WHERE id = :id
                        """,
                Map.of("name", user.getName(),
                        "login", user.getLogin(),
                        "password", user.getPassword(),
                        "id", id)
        ) > 0;
    }

}

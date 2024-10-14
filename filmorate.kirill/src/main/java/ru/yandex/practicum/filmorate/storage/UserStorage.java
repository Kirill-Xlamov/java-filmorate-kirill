package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    Map<Integer, User> getUsers();

    List<User> findAll();

    User create(User user);

    User update(User user);

    User get(Integer id);
}
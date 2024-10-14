package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    protected final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public Map<Integer, User> getUsers() {
        return new HashMap<>(users);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        setUserLocalId(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        int userId = user.getId();
        users.put(userId, user);
        return user;
    }

    @Override
    public User get(Integer id) {
        return users.get(id);
    }

    private void setUserLocalId(User user) {
        user.setId(id);
        id++;
    }
}
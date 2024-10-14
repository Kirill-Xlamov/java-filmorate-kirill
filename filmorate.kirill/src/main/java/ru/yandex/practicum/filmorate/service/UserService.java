package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserStorage {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> addFriend(Integer userId, Integer friendId) {
        User user = get(userId);
        User friend = get(friendId);
        Set<Integer> userFriends = user.getFriends();
        if (userFriends.add(friendId)) {
            log.info("К пользователю id:{}, добавлен друг id:{}", userId, friendId);
        }
        if (friend.getFriends().add(userId)) {
            log.info("К пользователю id:{}, добавлен друг id:{}", friendId, userId);
        }
        return userFriends.stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    public List<User> removeFriend(Integer userId, Integer friendId) {
        User user = get(userId);
        User friend = get(friendId);
        Set<Integer> userFriends = user.getFriends();
        Set<Integer> friendFriends = friend.getFriends();
        userFriends.remove(friendId);
        log.info("У пользователя id:{}, удален друг id:{}", userId, friendId);
        friendFriends.remove(userId);
        log.info("У пользователя id:{}, удален друг id:{}", friendId, userId);
        return userFriends.stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(Integer userId) {
        User user = get(userId);
        log.info("У пользователя id:{} получен список друзей", userId);
        return user.getFriends().stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User user = get(userId);
        User otherUser = get(otherId);
        Set<Integer> friends = user.getFriends();
        Set<Integer> otherFriends = otherUser.getFriends();
        log.info("У пользователя id:{} получен общий список друзей с пользователем с id:{}", userId, otherId);
        return friends.stream()
                .filter(otherFriends::contains)
                .map(this::get)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, User> getUsers() {
        log.info("Количество пользователей {}", userStorage.getUsers().size());
        return userStorage.getUsers();
    }

    @Override
    public List<User> findAll() {
        log.info("Количество найденых пользователей {}", userStorage.findAll().size());
        return userStorage.findAll();
    }

    @Override
    public User create(User user) {
        if (user.getId() != 0) {
            throw new ValidationException("При добавлении id должен быть 0");
        }
        String userLogin = user.getLogin();
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Установлено имя {}", userLogin);
            user.setName(userLogin);
        }
        User userAdded = userStorage.create(user);
        log.info("Добавлен новый пользователь {}", userAdded);
        return userAdded;
    }

    @Override
    public User update(User user) {
        int userId = user.getId();
        if (userId == 0 || !getUsers().containsKey(userId)) {
            throw new ObjectNotFoundException("Введите пользователя, которого надо обновить");
        }
        User userUpdated = userStorage.update(user);
        log.info("Пользователь обновлен: {}", userUpdated);
        return userUpdated;
    }

    @Override
    public User get(Integer id) {
        if (!getUsers().containsKey(id)) {
            throw new ObjectNotFoundException(String.format("Пользователь с id=%s не найден", id));
        }
        log.info("Получен пользователь с id: {}", id);
        return userStorage.get(id);
    }
}
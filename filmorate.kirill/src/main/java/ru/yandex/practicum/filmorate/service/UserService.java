package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

	List<User> addFriend(int userId, int friendId);

	List<User> removeFriend(int userId, int friendId);

	List<User> getFriends(int userId);

	List<User> getCommonFriends(int userId, int otherId);

	Map<Integer, User> getUsers();

	List<User> findAll();

	User create(User user);

	User update(User user);

	User get(int id);
}
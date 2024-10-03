package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
	private final UserStorage userStorage;
	User user;
	User friend;

	@Autowired
	public UserService(UserStorage userStorage) {
		this.userStorage = userStorage;
	}

	public List<User> addFriend(Integer userId, Integer friendId) {
		user = get(userId);
		friend = get(friendId);
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
		user = get(userId);
		friend = get(friendId);
		Set<Integer> friends = user.getFriends();
		friends.remove(friendId);
		friend.getFriends().remove(userId);
		log.info("У пользователя id:{}, удален друг id:{}", userId, friendId);
		return friends.stream()
				.map(this::get)
				.collect(Collectors.toList());
	}

	public List<User> getFriends(Integer userId) {
		user = get(userId);
		return user.getFriends().stream()
				.map(this::get)
				.collect(Collectors.toList());
	}

	public List<User> commonFriends(Integer userId, Integer otherId) {
		user = get(userId);
		friend = get(otherId);
		Set<Integer> friends = user.getFriends();
		Set<Integer> otherFriends = friend.getFriends();
		return friends.stream()
				.filter(otherFriends::contains)
				.map(this::get)
				.collect(Collectors.toList());
	}

	public List<User> findAll() {
		return userStorage.findAll();
	}

	public User create(User user) {
		return userStorage.create(user);
	}

	public User update(User user) {
		return userStorage.update(user);
	}

	public User get(Integer id) {
		return userStorage.get(id);
	}
}
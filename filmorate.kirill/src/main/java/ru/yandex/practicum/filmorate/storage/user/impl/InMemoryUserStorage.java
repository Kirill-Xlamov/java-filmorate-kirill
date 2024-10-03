package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

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
	public List<User> findAll() {
		log.info("Количество пользователей {}", users.size());
		return new ArrayList<>(users.values());
	}

	@Override
	public User create(User user) {
		if (user.getId() != 0) {
			throw new ValidationException("При добавлении id должен быть 0");
		}
		String userLogin = user.getLogin();
		if (user.getName() == null || user.getName().isBlank()) {
			user.setName(userLogin);
		}
		setUserLocalId(user);
		users.put(user.getId(), user);
		log.info("Добавлен новый пользователь {}", user);
		return user;
	}

	@Override
	public User update(User user) {
		int userId = user.getId();
		if (userId == 0 || !users.containsKey(userId)) {
			throw new ObjectNotFoundException("Введите пользователя, которого надо обновить");
		}
		users.put(userId, user);
		log.info("Пользователь обновлен: {}", user);
		return user;
	}

	@Override
	public User get(Integer id) {
		if (!users.containsKey(id)) {
			throw new ObjectNotFoundException(String.format("Пользователь с id=%s не найден", id));
		}
		return users.get(id);
	}

	private void setUserLocalId(User user) {
		user.setId(id);
		id++;
	}
}
package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	private final Map<Integer, User> users = new HashMap<>();
	private int id = 1;

	@GetMapping
	public List<User> findAll() {
		log.info("Количество пользователей {}", users.size());
		return new ArrayList<>(users.values());
	}

	@PostMapping
	public User create(@Valid @RequestBody User user) throws ValidationException {
		String userLogin = user.getLogin();

		if (user.getName() == null || user.getName().isBlank()) {
			user.setName(userLogin);
		}
		if (user.getId() != 0) {
			throw new ValidationException("При добавлении id должен быть 0");
		}
		setUserLocalId(user);
		users.put(user.getId(), user);
		log.info("Добавлен новый пользователь {}", user);
		return user;
	}

	@PutMapping
	public User update(@Valid @RequestBody User user) throws ValidationException {
		int userId = user.getId();
		if (userId == 0 || !users.containsKey(userId)) {
			throw new ValidationException("Введите пользователя, которого надо обновить");
		}
		users.put(userId, user);
		log.info("Пользователь обновлен: {}", user);
		return user;
	}

	private void setUserLocalId(User user) {
		user.setId(id);
		id++;
	}
}
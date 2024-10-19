package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> findAll() {
		return userService.findAll();
	}

	@PostMapping
	public User create(@Validated @RequestBody User user) {
		return userService.create(user);
	}

	@PutMapping
	public User update(@Validated @RequestBody User user) {
		return userService.update(user);
	}

	@PutMapping("/{id}/friends/{friendId}")
	public List<User> addFriend(@PathVariable int id, @PathVariable int friendId) {
		return userService.addFriend(id, friendId);
	}

	@DeleteMapping("/{id}/friends/{friendId}")
	public List<User> removeFriend(@PathVariable int id, @PathVariable int friendId) {
		return userService.removeFriend(id, friendId);
	}

	@GetMapping("/{id}/friends")
	public List<User> getFriends(@PathVariable int id) {
		return userService.getFriends(id);
	}

	@GetMapping("/{id}/friends/common/{otherId}")
	public List<User> commonFriends(@PathVariable int id, @PathVariable int otherId) {
		return userService.getCommonFriends(id, otherId);
	}
}
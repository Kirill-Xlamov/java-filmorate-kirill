package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
	Map<Integer, Film> films = new HashMap<>();
	private final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
	private int id = 1;

	@GetMapping
	public List<Film> findAll() {
		log.info("Количество фильмов {}", films.size());
		return new ArrayList<>(films.values());
	}

	@PostMapping
	public Film add(@Valid @RequestBody Film film) throws ValidationException {
		if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
			log.info("Не пройдена валидация releaseDate");
			throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
		}
		if (film.getId() != 0) {
			throw new ValidationException("При добавлении id должен быть 0");
		}
		setLocalFilmId(film);
		films.put(film.getId(), film);
		log.info("Добавлен новый фильм {}", film);
		return film;
	}

	@PutMapping
	public Film update(@Valid @RequestBody Film film) throws ValidationException {
		int filmId = film.getId();
		if (!films.containsKey(filmId) || filmId == 0) {
			throw new ValidationException("Введите фильм, который надо обновить");
		}
		films.put(filmId, film);
		log.info("Фильм обновлен: {}", film);
		return film;
	}

	private void setLocalFilmId(Film film) {
		film.setId(id);
		id++;
	}
}

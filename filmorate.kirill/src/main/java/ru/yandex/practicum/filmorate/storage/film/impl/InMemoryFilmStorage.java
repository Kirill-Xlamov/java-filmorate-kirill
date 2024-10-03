package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
	private final Map<Integer, Film> films = new HashMap<>();
	private final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
	private int id = 1;

	@Override
	public List<Film> findAll() {
		log.info("Количество фильмов {}", films.size());
		return new ArrayList<>(films.values());
	}

	@Override
	public Film add(Film film) {
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

	@Override
	public Film update(Film film) {
		int filmId = film.getId();
		if (!films.containsKey(filmId) || filmId == 0) {
			throw new ObjectNotFoundException("Введите фильм, который надо обновить");
		}
		films.put(filmId, film);
		log.info("Фильм обновлен: {}", film);
		return film;
	}

	public Film get(Integer id) {
		if (!films.containsKey(id)) {
			throw new ObjectNotFoundException(String.format("Фильм с %s не найден", id));
		}
		return films.get(id);
	}

	private void setLocalFilmId(Film film) {
		film.setId(id);
		id++;
	}
}
package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmService {
	Film addLike(int filmId, int userId);

	Film removeLike(int filmId, int userId);

	List<Film> getPopularFilms(int count);

	Film get(int id);

	Film add(Film film);

	Map<Integer, Film> getFilms();

	Film update(Film film);

	List<Film> findAll();
}
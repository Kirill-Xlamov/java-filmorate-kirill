package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
	private final FilmStorage filmStorage;
	private final UserStorage userStorage;
	Film film;

	@Autowired
	public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
		this.filmStorage = filmStorage;
		this.userStorage = userStorage;
	}

	public Film addLike(Integer filmId, Integer userId) {
		film = get(filmId);
		Set<Integer> likesByUser = film.getLikesByUser();
		userStorage.get(userId);
		likesByUser.add(userId);
		return film;
	}

	public Film removeLike(Integer filmId, Integer userId) {
		film = get(filmId);
		Set<Integer> likesByUser = film.getLikesByUser();
		userStorage.get(userId);
		likesByUser.remove(userId);
		return film;
	}

	public List<Film> popularFilms(Integer count) {
		return filmStorage.findAll().stream()
				.sorted(FilmService::compare)
				.limit(count)
				.collect(Collectors.toList());
	}

	private static int compare(Film film1, Film film2) {
		int sizeFilm1Likes = film1.getLikesByUser().size();
		int sizeFilm2Likes = film2.getLikesByUser().size();
		return sizeFilm2Likes - sizeFilm1Likes;
	}

	public Film get(Integer id) {
		return filmStorage.get(id);
	}

	public Film add(Film film) {
		return filmStorage.add(film);
	}

	public Film update(Film film) {
		return filmStorage.update(film);
	}

	public List<Film> findAll() {
		return filmStorage.findAll();
	}
}
package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Map<Integer, Film> getFilms();

    Film add(Film film);

    Film update(Film film);

    List<Film> findAll();

    Film get(Integer id);
}
package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @Override
    public Map<Integer, Film> getFilms() {
        return new HashMap<>(films);
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        setLocalFilmId(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        int filmId = film.getId();
        films.put(filmId, film);
        return film;
    }

    @Override
    public Film get(Integer id) {
        return films.get(id);
    }

    private void setLocalFilmId(Film film) {
        film.setId(id);
        id++;
    }
}
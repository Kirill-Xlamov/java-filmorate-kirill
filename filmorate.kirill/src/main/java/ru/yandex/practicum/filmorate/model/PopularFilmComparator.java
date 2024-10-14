package ru.yandex.practicum.filmorate.model;

import java.util.Comparator;

public class PopularFilmComparator implements Comparator<Film> {
    @Override
    public int compare(Film film1, Film film2) {
        int sizeFilm1Likes = film1.getLikesByUser().size();
        int sizeFilm2Likes = film2.getLikesByUser().size();
        return sizeFilm2Likes - sizeFilm1Likes;
    }
}
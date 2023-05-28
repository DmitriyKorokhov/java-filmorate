package ru.yandex.practicum.javafilmorate.storage;

public interface LikesDao {
    void addLike(int id, int userId);

    void deleteLike(int id, int userId);
}

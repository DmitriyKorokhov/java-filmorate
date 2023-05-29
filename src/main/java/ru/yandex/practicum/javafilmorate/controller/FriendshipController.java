package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.FriendshipService;

import java.util.List;

@RequestMapping("/users")
@Slf4j
@RestController
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        friendshipService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriendById(@PathVariable int id, @PathVariable int friendId) {
        friendshipService.deleteFriendById(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getOfAllUsersFriends(@PathVariable int id) {
        return friendshipService.getOfAllUsersFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return friendshipService.getCommonFriends(id, otherId);
    }
}

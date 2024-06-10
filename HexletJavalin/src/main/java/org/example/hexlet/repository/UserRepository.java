package org.example.hexlet.repository;

import lombok.Getter;
import org.example.hexlet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.eclipse.jetty.util.StringUtil.startsWithIgnoreCase;

public class UserRepository {
    @Getter
    private static List<User> entities = new ArrayList<>();

    public static void save(User user) {
        user.setId((long) entities.size() + 1);
        entities.add(user);
    }

    public static List<User> search(String term) {
        return entities.stream()
                .filter(entity -> startsWithIgnoreCase(entity.getName(), term))
                .toList();
    }

    public static Optional<User> find(Long id) {
        return entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }

    public static void delete(Long id) {

    }

}

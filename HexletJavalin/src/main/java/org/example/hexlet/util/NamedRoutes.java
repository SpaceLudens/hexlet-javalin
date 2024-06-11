package org.example.hexlet.util;

public class NamedRoutes {
    public static String userPath() {
        return "/users";
    }

    public static String buildUserPath() {
        return "/users/build";
    }

    public static String userPath(Long id) {
        return userPath(String.valueOf(id));
    }

    public static String userPath(String id) {
        return "/users/" + id;
    }

    public static String coursePath() {
        return "/courses";
    }

    public static String buildCoursePath() {
        return "/courses/build";
    }

    public static String coursePath(Long id) {
        return coursePath(String.valueOf(id));
    }

    public static String coursePath(String id) {
        return "/courses/" + id;
    }
}

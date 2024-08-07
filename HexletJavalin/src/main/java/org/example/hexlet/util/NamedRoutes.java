package org.example.hexlet.util;

public class NamedRoutes {
    //USER
    public static String usersPath() {
        return "/users";
    }

    public static String buildUsersPath() {
        return "/users/build";
    }

    public static String usersPath(Long id) {
        return usersPath(String.valueOf(id));
    }

    public static String usersPath(String id) {
        return "/users/" + id;
    }

    //COURSE
    public static String coursesPath() {
        return "/courses";
    }

    public static String buildCoursesPath() {
        return "/courses/build";
    }

    public static String coursesPath(Long id) {
        return coursesPath(String.valueOf(id));
    }

    public static String coursesPath(String id) {
        return "/courses/" + id;
    }

    //SESSION
    public static String buildSessionsPath() {
        return "/sessions/build";
    }

    public static String sessionsPath() {
        return "/sessions";
    }
}

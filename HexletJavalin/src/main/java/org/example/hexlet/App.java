package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static org.example.hexlet.util.NamedRoutes.*;

import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.UsersController;

import java.util.List;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.render("index.jte"));
        //COURSES
        app.get(buildCoursesPath(), CoursesController::build);
        app.get(coursesPath("{id}"), CoursesController::show);
        app.get(coursesPath(), CoursesController::index);
        app.post(coursesPath(), CoursesController::create);
        app.get("/courses/{id}/edit", CoursesController::edit);
        app.patch("/courses/{id}", CoursesController::update);
        app.delete("/courses/{id}", CoursesController::destroy);

        //USERS
        app.get(buildUsersPath(), UsersController::build);
        app.get(usersPath("{id}"), UsersController::show);
        app.get(usersPath(), UsersController::index);
        app.post(usersPath(), UsersController::create);
        app.get("/users/{id}/edit", UsersController::edit);
        app.patch("/users/{id}", UsersController::update);
        app.delete("/users/{id}", UsersController::destroy);

        app.start(7070);
    }
}
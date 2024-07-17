package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.example.hexlet.util.NamedRoutes.*;

import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.util.NamedRoutes;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> {
            var page = new MainPage(ctx.sessionAttribute("currentUser"));
            ctx.render("index.jte", model("page", page));
        });
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
        app.patch(usersPath("{id}"), UsersController::update);
        app.delete(usersPath("{id}"), UsersController::destroy);

        //SESSION
        app.get(NamedRoutes.buildSessionsPath(), SessionsController::build);
        app.post(NamedRoutes.sessionsPath(), SessionsController::create);
        app.delete(NamedRoutes.sessionsPath(), SessionsController::destroy);

        app.start(7070);
    }
}
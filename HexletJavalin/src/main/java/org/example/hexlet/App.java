package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.example.hexlet.util.NamedRoutes.*;

import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited"));
            var page = new MainPage(visited);
            ctx.render("index.jte", model("page", page));
            ctx.cookie("visited", String.valueOf(true));

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

        app.start(7070);
    }
}
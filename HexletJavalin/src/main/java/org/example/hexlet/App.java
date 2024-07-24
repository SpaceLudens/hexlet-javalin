package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.example.hexlet.util.NamedRoutes.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.repository.BaseRepository;
import org.example.hexlet.util.NamedRoutes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class App {
    public static Javalin getApp() throws IOException, SQLException{
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:hexlet_project;DB_CLOSE_DELAY=-1;");

        var dataSource = new HikariDataSource(hikariConfig);

        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines()
                .collect(Collectors.joining("\n"));

        try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;
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
        
        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Javalin app = getApp();
        app.start(7070);
    }
}
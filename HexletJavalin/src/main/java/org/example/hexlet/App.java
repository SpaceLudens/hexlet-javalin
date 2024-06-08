package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.hexlet.data.CourseData;
import org.example.hexlet.data.UserData;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.render("index.jte"));

        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var course = CourseData.getCourses().stream()
                    .filter(c -> c.getId() == Integer.parseInt(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/courses", ctx -> {
            var courses = CourseData.getCourses();
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var user = UserData.getUsers().stream()
                    .filter(c -> c.getId() == Integer.parseInt(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new UserPage(user);
            ctx.render("users/show.jte", model("page", page));
        });
        app.get("/users", ctx -> {
            var users = UserData.getUsers();
            var page = new UsersPage(users);
            ctx.render("users/index.jte", model("page", page));
        });


        app.start(7070);
    }
}
package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;
import static org.eclipse.jetty.util.StringUtil.startsWithIgnoreCase;

import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import java.util.List;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.render("index.jte"));

        app.get("/courses/build", ctx -> {
            ctx.render("courses/build.jte");
        });
        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var course = CourseRepository.getEntities().stream()
                    .filter(c -> c.getId() == Integer.parseInt(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            List<Course> courses;
            if (term != null) {
                courses = CourseRepository.getEntities().stream()
                        .filter(c -> startsWithIgnoreCase(c.getName(),term))
                        .toList();
            } else {
                courses = CourseRepository.getEntities();
            }
            var page = new CoursesPage(courses, term);
            ctx.render("courses/index.jte", model("page", page));
        });
        app.post("/courses", ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.redirect("/courses");
        });

        app.get("/users/build", ctx -> {
           ctx.render("users/build.jte");
        });
        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var user = UserRepository.getEntities().stream()
                    .filter(c -> c.getId() == Integer.parseInt(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new UserPage(user);
            ctx.render("users/show.jte", model("page", page));
        });
        app.get("/users", ctx -> {
            var term = ctx.queryParam("term");
            List<User> users;
            if (term != null) {
                users = UserRepository.getEntities().stream()
                        .filter(u -> startsWithIgnoreCase(u.getName(), term))
                        .toList();
            } else {
                users = UserRepository.getEntities();
            }
            var page = new UsersPage(users, term);
            ctx.render("users/index.jte", model("page", page));
        });
        app.post("/users", ctx -> {
            var name = ctx.formParam("name");
            var email = ctx.formParam("email");
            var password = ctx.formParam("password").toLowerCase();
            var passwordConfirmation = ctx.formParam("passwordConfirmation");

            var user = new User(name, email, password);
            UserRepository.save(user);
            ctx.redirect("/users");
        });

        app.start(7070);
    }
}
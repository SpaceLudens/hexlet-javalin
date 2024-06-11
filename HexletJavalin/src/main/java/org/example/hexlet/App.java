package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;
import static org.eclipse.jetty.util.StringUtil.startsWithIgnoreCase;
import static org.example.hexlet.util.NamedRoutes.*;

import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
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
        //COURSES
        app.get(buildCoursePath(), ctx -> {
            var page = new BuildCoursePage();
            ctx.render("courses/build.jte", model("page", page));
        });

        app.get(coursePath("{id}"), ctx -> {
            var id = ctx.pathParam("id");
            var course = CourseRepository.getEntities().stream()
                    .filter(c -> c.getId() == Integer.parseInt(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get(coursePath(), ctx -> {
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

        app.post(coursePath(), ctx -> {
            try {
                var name = ctx.formParamAsClass("name", String.class)
                        .check(val -> val.length() > 2, "Длинна названия курса не может быть меньше 2 символов")
                        .get();
                var description = ctx.formParamAsClass("description", String.class)
                        .check(val -> val.length() > 10, "Длинна описания курса не может быть меньше 10 символов")
                        .get();
                var course = new Course(name, description);
                CourseRepository.save(course);
                ctx.redirect("/courses");
            } catch (ValidationException e) {
                var name = ctx.formParam("name");
                var description = ctx.formParam("description");
                var page = new BuildCoursePage(name, description, e.getErrors());
                ctx.render("courses/build.jte", model("page", page));
            }
        });
        //USERS
        app.get(buildUserPath(), ctx -> {
            var page = new BuildUserPage();
           ctx.render("users/build.jte", model("page", page));
        });

        app.get(userPath("{id}"), ctx -> {
            var id = ctx.pathParam("id");
            var user = UserRepository.getEntities().stream()
                    .filter(c -> c.getId() == Integer.parseInt(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new UserPage(user);
            ctx.render("users/show.jte", model("page", page));
        });

        app.get(userPath(), ctx -> {
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

        app.post(userPath(), ctx -> {
            var name = ctx.formParam("name");
            var email = ctx.formParam("email");

            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(val -> val.equals(passwordConfirmation), "Пароли не совпадают")
                        .check(val -> val.length() > 6, "У пароля недостаточная длинна")
                        .get();
                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/build.jte", model("page", page));
            }


        });
        app.start(7070);
    }
}
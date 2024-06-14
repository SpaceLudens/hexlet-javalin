package org.example.hexlet.controller;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.eclipse.jetty.util.StringUtil.startsWithIgnoreCase;
import static org.example.hexlet.util.NamedRoutes.*;

import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.List;

public class UsersController {
    public static void index(Context context) {
        var term = context.queryParam("term");
        List<User> users;
        if (term != null) {
            users = UserRepository.getEntities()
                    .stream()
                    .filter(u -> startsWithIgnoreCase(u.getName(), term))
                    .toList();
        } else {
            users = UserRepository.getEntities();
        }
        var page = new UsersPage(users, term);
        context.render("users/index.jte", model("page", page));
    }

    public static void show(Context context) {
        var id = context.pathParam("id");
        var course = CourseRepository.getEntities().stream()
                .filter(c -> c.getId() == Integer.parseInt(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
        var page = new CoursePage(course);
        context.render("courses/show.jte", model("page", page));
    }

    public static void build(Context context) {
        var page = new BuildCoursePage();
        context.render("courses/build.jte", model("page", page));
    }

    public static void create(Context context) {
        try {
            var name = context.formParamAsClass("name", String.class)
                    .check(val -> val.length() > 2, "Длинна названия курса не может быть меньше 2 символов")
                    .get();
            var description = context.formParamAsClass("description", String.class)
                    .check(val -> val.length() > 10, "Длинна описания курса не может быть меньше 10 символов")
                    .get();
            var course = new Course(name, description);
            CourseRepository.save(course);
            context.redirect("/courses");
        } catch (ValidationException e) {
            var name = context.formParam("name");
            var description = context.formParam("description");
            var page = new BuildCoursePage(name, description, e.getErrors());
            context.render("courses/build.jte", model("page", page));
        }
    }

    public static void edit(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();

        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new CoursePage(course);
        context.render("courses/edit.jte", model("page", page));
    }

    public static void update(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        var name = context.formParam("name");
        var description = context.formParam("description");

        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        course.setName(name);
        course.setDescription(description);
        CourseRepository.save(course);
        context.redirect(coursesPath());
    }

    public static void destroy(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        UserRepository.delete(id);
        context.redirect(usersPath());
    }
}

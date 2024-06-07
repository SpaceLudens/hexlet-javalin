package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.hexlet.data.Data;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/courses", ctx -> {
            var courses = Data.getCourses();
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get("/courses/{id}", ctx -> {
           var id = ctx.pathParam("id");
           var course = Data.getCourses().stream()
                   .filter(c -> c.getId() == Integer.parseInt(id))
                   .findFirst()
                   .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
           var page = new CoursePage(course);
           ctx.render("courses/show.jte", model("page", page));
        });

        app.start(7070);
    }
}
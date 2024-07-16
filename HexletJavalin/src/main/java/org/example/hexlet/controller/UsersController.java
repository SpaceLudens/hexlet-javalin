package org.example.hexlet.controller;

import static io.javalin.rendering.template.TemplateUtil.model;
import static org.eclipse.jetty.util.StringUtil.startsWithIgnoreCase;
import static org.example.hexlet.util.NamedRoutes.*;

import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.User;
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
        var user = UserRepository.getEntities().stream()
                .filter(c -> c.getId() == Integer.parseInt(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundResponse("User with id = " + id + " not found"));
        var page = new UserPage(user);
        context.render("users/show.jte", model("page", page));
    }

    public static void build(Context context) {
        var page = new BuildUserPage();
        context.render("users/build.jte", model("page", page));
    }

    public static void create(Context context) {
        try {

            var name = context.formParamAsClass("name", String.class)
                    .check(val -> val.length() > 2, "The length of the name cannot be less than 2")
                    .get()
                    .trim();
            var email = context.formParamAsClass("email", String.class)
                    .check(val -> val.length() > 10, "The length of the email cannot be less than 10")
                    .get()
                    .toLowerCase()
                    .trim();
            var passwordConfirmation = context.formParam("passwordConfirmation");
            var password = context.formParamAsClass("password", String.class)
                    .check(val -> val.equals(passwordConfirmation),"Passwords don't match")
                    .check(val -> val.length() > 6, "The password is not long enough")
                    .get();
            var user = new User(name, email, password);
            UserRepository.save(user);
            context.redirect("/users");
        } catch (ValidationException e) {
            var name = context.formParam("name");
            var email = context.formParam("email");
            var page = new BuildUserPage(name, email, e.getErrors());
            context.render("users/build.jte", model("page", page));
        }
    }

    public static void edit(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();

        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UserPage(user);
        context.render("users/edit.jte", model("page", page));
    }

    public static void update(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        var name = context.formParam("name");
        var email = context.formParam("email");
        var password = context.formParam("password");
        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        UserRepository.save(user);
        context.redirect(usersPath());
    }

    public static void destroy(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        UserRepository.delete(id);
        context.redirect(usersPath());
    }
}

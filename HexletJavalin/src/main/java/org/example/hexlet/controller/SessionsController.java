package org.example.hexlet.controller;

import io.javalin.http.Context;

public class SessionsController {

    public static void build(Context context) {
        context.render("sessions/build.jte");
    }

    public static void create(Context context) {
        var nickname = context.formParam("nickname");
        var password = context.formParam("password");
        context.sessionAttribute("currentUser", nickname);
        context.redirect("/");
    }

    public static void destroy(Context context) {
        context.sessionAttribute("currentUser", null);
        context.redirect("/");
    }


}

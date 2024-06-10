package org.example.hexlet.data;

import java.util.Random;
import java.util.Locale;
import net.datafaker.Faker;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;
import org.example.hexlet.model.User;
import java.util.stream.LongStream;

public class UserData {
    private static final long ITEMS_COUNT = 30;


    public static ArrayList<User> getUsers() {
        Random random = new Random(123);
        Faker faker = new Faker(new Locale("en"), random);
        List<Long> ids = LongStream
                .range(1, ITEMS_COUNT + 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(ids, random);

        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < ITEMS_COUNT; i++) {
            var id = ids.get(i);
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var email = faker.internet().emailAddress();
            User user = new User(id, firstName, lastName, email);
            users.add(user);
        }

        return users;
    }

}
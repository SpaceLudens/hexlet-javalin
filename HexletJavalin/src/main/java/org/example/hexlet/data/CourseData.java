package org.example.hexlet.data;

import org.example.hexlet.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseData {
    public static List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        Course c1 = new Course("Java", "course description");
        Course c2 = new Course("Python", "course description");
        Course c3 = new Course("JavaScript", "course description");
        Course c4 = new Course("Go", "course description");
        Course c5 = new Course("Ruby", "course description");

        c1.setId(1L);
        c2.setId(2L);
        c3.setId(3L);
        c4.setId(4L);
        c5.setId(5L);

        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
        courses.add(c4);
        courses.add(c5);

        return courses;
    }
}

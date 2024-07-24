package org.example.hexlet.repository;

import org.example.hexlet.model.Course;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository extends BaseRepository{
    public static void save(Course course)  throws SQLException {
        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Course> find(Long id) throws SQLException{
        var sql = "SELECT * FROM courses WHERE id = ?";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var description = resultSet.getString("description");
                var course = new Course(name, description);
                course.setId(id);
                return Optional.of(course);
            }
            return Optional.empty();
        }
    }

    public static List<Course> getEntities() throws SQLException {
        var sql = "SELECT * FROM courses";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            var result = new ArrayList<Course>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var description = resultSet.getString("description");
                var course = new Course(name, description);
                course.setId(id);
                result.add(course);
            }
            return result;
        }
    }
}

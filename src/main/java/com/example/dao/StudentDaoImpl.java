package com.example.dao;

import com.example.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private RowMapper<Student> rowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
        }
    };

    @Override
    public void create(Student s) {
        jdbcTemplate.update("INSERT INTO students (id, name, email) VALUES (?, ?, ?)",
                s.getId(), s.getName(), s.getEmail());
    }

    @Override
    public Student read(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM students WHERE id=?", rowMapper, id);
    }

    @Override
    public List<Student> readAll() {
        return jdbcTemplate.query("SELECT * FROM students", rowMapper);
    }

    @Override
    public void update(Student s) {
        jdbcTemplate.update("UPDATE students SET name=?, email=? WHERE id=?",
                s.getName(), s.getEmail(), s.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM students WHERE id=?", id);
    }
}

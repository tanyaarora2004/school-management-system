package com.example.dao;

import com.example.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate jdbcTemplate;

    // Spring will inject JdbcTemplate
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map SQL result to Student object
    private RowMapper<Student> rowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age"),
                    rs.getInt("rollNo")
            );
        }
    };

    @Override
    public void create(Student s) {
        String sql = "INSERT INTO students(id, name, email, age, rollNo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, s.getId(), s.getName(), s.getEmail(), s.getAge(), s.getRollNo());
    }


    @Override
    public Student read(int id) {
        String sql = "SELECT * FROM students WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null; // so your menu can print "Student not found" instead of crashing
        }
    }


    @Override
    public List<Student> readAll() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Student s) {
        String sql = "UPDATE students SET name=?, email=?, age=?, rollNo=? WHERE id=?";
        jdbcTemplate.update(sql, s.getName(), s.getEmail(), s.getAge(), s.getRollNo(), s.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}

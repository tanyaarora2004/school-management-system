package com.example.dao;

import com.example.model.Attendance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class AttendanceDaoImpl implements AttendanceDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void markAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                attendance.getStudentId(),
                Date.valueOf(attendance.getDate()), // LocalDate → java.sql.Date
                attendance.getStatus());
    }

    @Override
    public List<Attendance> getAttendanceByStudent(int studentId) {
        String sql = "SELECT * FROM attendance WHERE student_id = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, new RowMapper<Attendance>() {
            @Override
            public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
                Attendance a = new Attendance();
                a.setId(rs.getInt("id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setDate(rs.getDate("date").toLocalDate()); // java.sql.Date → LocalDate
                a.setStatus(rs.getString("status"));
                return a;
            }
        });
    }

    @Override
    public double getAttendancePercentage(int studentId) {
        String totalSql = "SELECT COUNT(*) FROM attendance WHERE student_id = ?";
        String presentSql = "SELECT COUNT(*) FROM attendance WHERE student_id = ? AND status = 'P'";

        Integer totalClasses = jdbcTemplate.queryForObject(totalSql, Integer.class, studentId);
        Integer presentClasses = jdbcTemplate.queryForObject(presentSql, Integer.class, studentId);

        if (totalClasses == null || totalClasses == 0) {
            return 0.0;
        }
        return (presentClasses * 100.0) / totalClasses;
    }

}

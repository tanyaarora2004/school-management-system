package com.example.dao;

import com.example.model.Attendance;
import java.util.List;

public interface AttendanceDao {
    void markAttendance(Attendance attendance);
    List<Attendance> getAttendanceByStudent(int studentId);
    double getAttendancePercentage(int studentId);
}

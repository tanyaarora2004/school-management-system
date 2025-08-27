package com.example.service;

import com.example.model.Attendance;
import java.util.List;

public interface AttendanceService {
    void markAttendance(Attendance attendance);
    List<Attendance> getAttendanceByStudent(int studentId);
    double getAttendancePercentage(int studentId);
}

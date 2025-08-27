package com.example.service;

import com.example.dao.AttendanceDao;
import com.example.model.Attendance;
import java.util.List;

public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceDao attendanceDao;

    public void setAttendanceDao(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    @Override
    public void markAttendance(Attendance attendance) {
        attendanceDao.markAttendance(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByStudent(int studentId) {
        return attendanceDao.getAttendanceByStudent(studentId);
    }

    @Override
    public double getAttendancePercentage(int studentId) {
        return attendanceDao.getAttendancePercentage(studentId);
    }
}

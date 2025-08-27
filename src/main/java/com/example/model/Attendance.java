package com.example.model;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private int studentId;
    private LocalDate date;  // ✅ Use LocalDate
    private String status;

    public Attendance() {}

    public Attendance(int id, int studentId, LocalDate date, String status) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}

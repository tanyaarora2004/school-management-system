package com.example.service;

import com.example.model.Student;
import java.util.List;

public interface StudentService {
    void addStudent(Student s);
    Student getStudent(int id);
    List<Student> getAll();
    void updateStudent(Student s);
    void deleteStudent(int id);
}

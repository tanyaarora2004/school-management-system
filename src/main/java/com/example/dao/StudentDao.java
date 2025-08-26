package com.example.dao;

import com.example.model.Student;
import java.util.List;

public interface    StudentDao {
    void create(Student s);
    Student read(int id);
    List<Student> readAll();
    void update(Student s);
    void delete(int id);
}

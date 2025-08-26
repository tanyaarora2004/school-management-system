package com.example.service;

import com.example.dao.StudentDao;
import com.example.model.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;
    public void setStudentDao(StudentDao studentDao) { this.studentDao = studentDao; }

    @Override
    public void addStudent(Student s) { studentDao.create(s); }
    @Override
    public Student getStudent(int id) { return studentDao.read(id); }
    @Override
    public List<Student> getAll() { return studentDao.readAll(); }
    @Override
    public void updateStudent(Student s) { studentDao.update(s); }
    @Override
    public void deleteStudent(int id) { studentDao.delete(id); }
}

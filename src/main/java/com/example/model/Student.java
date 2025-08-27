package com.example.model;

public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private int rollNo;

    public Student() {}

    public Student(int id, String name, String email, int age, int rollNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.rollNo = rollNo;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", Roll No='" + rollNo + '\'' +
                '}';
    }
}

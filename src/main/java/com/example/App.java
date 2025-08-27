package com.example;

import com.example.model.Student;
import com.example.model.Attendance;
import com.example.service.StudentService;
import com.example.service.AttendanceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService studentService = ctx.getBean("studentService", StudentService.class);
        AttendanceService attendanceService = ctx.getBean("attendanceService", AttendanceService.class);

        // Create tables if they don't exist
        DataSource ds = ctx.getBean("dataSource", DataSource.class);
        try (Connection con = ds.getConnection(); Statement st = con.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    id INT PRIMARY KEY,
                    name VARCHAR(50),
                    email VARCHAR(50),
                    age INT,
                    rollNo INT
                )
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS attendance (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    student_id INT,
                    date DATE,
                    status ENUM('P','A') NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES students(id)
                )
            """);
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- School Management System ---");
        System.out.println("1. Login as Student");
        System.out.println("2. Login as Teacher");
        System.out.print("Enter your choice: ");
        int loginChoice = sc.nextInt();
        sc.nextLine(); // consume newline

        if (loginChoice == 1) {
            // Student portal
            int choice = 0;
            do {
                System.out.println("\n--- Student Menu ---");
                System.out.println("1. See Profile");
                System.out.println("2. See Grades");
                System.out.println("3. See Attendance");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter your Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        Student s = studentService.getStudent(id);
                        if (s != null) System.out.println(s);
                        else System.out.println("❌ Student not found!");
                    }
                    case 2 -> System.out.println("📘 Grades feature coming soon...");
                    case 3 -> {
                        System.out.print("Enter your Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        List<Attendance> records = attendanceService.getAttendanceByStudent(id);
                        if (records.isEmpty()) {
                            System.out.println("❌ No attendance records found!");
                        } else {
                            records.forEach(a ->
                                    System.out.printf("📅 %s → %s\n",
                                            a.getDate().format(DATE_FORMATTER),
                                            a.getStatus().equals("P") ? "Present ✅" : "Absent ❌")
                            );
                            double percentage = attendanceService.getAttendancePercentage(id);
                            System.out.printf("📊 Attendance Percentage: %.2f%%\n", percentage);
                        }
                    }
                    case 4 -> System.out.println("👋 Exiting Student Portal...");
                    default -> System.out.println("⚠️ Invalid choice. Try again!");
                }

            } while (choice != 4);

        } else if (loginChoice == 2) {
            // Teacher portal
            int choice = 0;
            do {
                System.out.println("\n--- Teacher Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Student by ID");
                System.out.println("3. View All Students");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Take Attendance");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                try {
                    choice = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Roll No: ");
                        int rollNo = sc.nextInt();
                        sc.nextLine();
                        studentService.addStudent(new Student(id, name, email, age, rollNo));
                        System.out.println("✅ Student added successfully!");
                    }
                    case 2 -> {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        Student s = studentService.getStudent(id);
                        if (s != null) System.out.println(s);
                        else System.out.println("❌ Student not found!");
                    }
                    case 3 -> {
                        List<Student> students = studentService.getAll();
                        if (!students.isEmpty()) students.forEach(System.out::println);
                        else System.out.println("❌ No students found!");
                    }
                    case 4 -> {
                        System.out.print("Enter ID of student to update: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        Student s = studentService.getStudent(id);
                        if (s != null) {
                            System.out.print("Enter new Name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter new Email: ");
                            String email = sc.nextLine();
                            System.out.print("Enter new Age: ");
                            int age = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter new Roll No: ");
                            int rollNo = sc.nextInt();
                            sc.nextLine();
                            s.setName(name);
                            s.setEmail(email);
                            s.setAge(age);
                            s.setRollNo(rollNo);
                            studentService.updateStudent(s);
                            System.out.println("✅ Student updated successfully!");
                        } else {
                            System.out.println("❌ Student not found!");
                        }
                    }
                    case 5 -> {
                        System.out.print("Enter ID of student to delete: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        studentService.deleteStudent(id);
                        System.out.println("✅ Student deleted successfully!");
                    }
                    case 6 -> {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Date (dd-MM-yyyy): ");
                        String dateStr = sc.nextLine();
                        LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
                        System.out.print("Enter Status (P/A): ");
                        String status = sc.nextLine().trim().toUpperCase();

                        Attendance attendance = new Attendance();
                        attendance.setStudentId(id);
                        attendance.setDate(date);
                        attendance.setStatus(status);

                        attendanceService.markAttendance(attendance);
                        System.out.println("✅ Attendance marked successfully!");
                    }
                    case 7 -> System.out.println("👋 Exiting Teacher Portal...");
                    default -> System.out.println("⚠️ Invalid choice. Try again!");
                }

            } while (choice != 7);

        } else {
            System.out.println("⚠️ Invalid login choice!");
        }

        sc.close();
    }
}

package com.example;

import com.example.model.Student;
import com.example.service.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService service = ctx.getBean("studentService", StudentService.class);

        // Create table if it doesn't exist
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
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- School Management System ---");
        System.out.println("1. Login as Student");
        System.out.println("2. Login as Teacher");
        System.out.print("Enter your choice: ");
        int loginChoice = sc.nextInt();
        sc.nextLine(); // consume newline

        if (loginChoice == 1) {
            // Student portal: View profile, grades, attendance
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
                        Student s = service.getStudent(id);
                        if (s != null) System.out.println(s);
                        else System.out.println("❌ Student not found!");
                    }
                    case 2 -> System.out.println("📘 Grades feature coming soon...");
                    case 3 -> System.out.println("📊 Attendance feature coming soon...");
                    case 4 -> System.out.println("👋 Exiting Student Portal...");
                    default -> System.out.println("⚠️ Invalid choice. Try again!");
                }

            } while (choice != 4);

        } else if (loginChoice == 2) {
            // Teacher portal: CRUD + attendance (attendance currently "coming soon")
            int choice = 0; // ✅ initialized here
            do {
                System.out.println("\n--- Teacher Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Student by ID");
                System.out.println("3. View All Students");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Take Attendance (coming soon)");
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
                        service.addStudent(new Student(id, name, email, age, rollNo));
                        System.out.println("✅ Student added successfully!");
                    }
                    case 2 -> {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        Student s = service.getStudent(id);
                        if (s != null) System.out.println(s);
                        else System.out.println("❌ Student not found!");
                    }
                    case 3 -> {
                        List<Student> students = service.getAll();
                        if (!students.isEmpty()) students.forEach(System.out::println);
                        else System.out.println("❌ No students found!");
                    }
                    case 4 -> {
                        System.out.print("Enter ID of student to update: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        Student s = service.getStudent(id);
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
                            service.updateStudent(s);
                            System.out.println("✅ Student updated successfully!");
                        } else {
                            System.out.println("❌ Student not found!");
                        }
                    }
                    case 5 -> {
                        System.out.print("Enter ID of student to delete: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        service.deleteStudent(id);
                        System.out.println("✅ Student deleted successfully!");
                    }
                    case 6 -> System.out.println("📝 Attendance feature coming soon...");
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

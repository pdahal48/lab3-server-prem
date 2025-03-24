package edu.franklin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentRepository {
    private final Map<String, Student> studentMap = new ConcurrentHashMap<>();

    private static int studentCounter = 1;

    public StudentID addStudent(Student student) {
        String id = String.valueOf(studentCounter++);
        Student studentWithId = student.toBuilder().setId(id).build();
        studentMap.put(id, studentWithId);
        return StudentID.newBuilder().setValue(id).build();
    }

    public Student getStudent(String id) {
        return studentMap.get(id);
    }
}

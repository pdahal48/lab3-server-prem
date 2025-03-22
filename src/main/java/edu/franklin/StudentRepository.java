package edu.franklin;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentRepository {
    private final Map<String, Student> studentMap = new ConcurrentHashMap<>();

    public StudentID addStudent(Student student) {
        studentMap.put(student.getId(), student);
        return StudentID.newBuilder().setValue(student.getId()).build();
    }

    public Optional<Student> getStudent(String id) {
        return Optional.ofNullable(studentMap.get(id));
    }
}

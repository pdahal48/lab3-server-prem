package edu.franklin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StudentServiceTest {

    @GrpcClient
    StudentServiceGrpc.StudentServiceBlockingStub studentService;

    @Test
    void testAddAndGetStudent() {
        Student student = Student.newBuilder()
                .setName("John Doe")
                .setPhone("555-1234")
                .setAge("20")
                .setZip("12345")
                .build();

        StudentID studentID = studentService.addStudent(student);
        assertEquals("1", studentID.getValue());

        Student retrievedStudent = studentService.getStudent(StudentID.newBuilder().setValue("123").build());
        assertNotNull(retrievedStudent);
        assertEquals("John Doe", retrievedStudent.getName());
        assertEquals("555-1234", retrievedStudent.getPhone());
        assertEquals("20", retrievedStudent.getAge());
        assertEquals("C12345", retrievedStudent.getZip());
    }

    @Test
    void testGetStudentNotFound() {
        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> {
            studentService.getStudent(StudentID.newBuilder().setValue("999").build());
        });

        assertEquals(io.grpc.Status.NOT_FOUND.getCode(), exception.getStatus().getCode());
    }
}

package edu.franklin;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;

@GrpcService
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Inject
    StudentRepository studentRepository;

    @Override
    public void addStudent(Student request, io.grpc.stub.StreamObserver<StudentID> responseObserver) {
        StudentID studentID = studentRepository.addStudent(request);
        responseObserver.onNext(studentID);
        responseObserver.onCompleted();
    }

@Override
    public void getStudent(StudentID request, StreamObserver<Student> responseObserver) {
        studentRepository.getStudent(request.getValue()).ifPresentOrElse(
            student -> {
                responseObserver.onNext(student);
                responseObserver.onCompleted();
            },
            () -> {
                responseObserver.onError(
                    Status.NOT_FOUND
                        .withDescription("Could not find student with id " + request.getValue())
                        .asRuntimeException()
                );
            }
        );
    }
}

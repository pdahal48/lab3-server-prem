package edu.franklin;

import org.jboss.logging.Logger;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    private final StudentRepository studentRepository = new StudentRepository();
    private static final Logger LOGGER = Logger.getLogger(StudentRepository.class);


    @Override
    public void addStudent(Student request, StreamObserver<StudentID> responseObserver) {
        StudentID studentID = studentRepository.addStudent(request);
        LOGGER.debug("Adding a new student: " + studentID);
        responseObserver.onNext(studentID);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudent(StudentID request, StreamObserver<Student> responseObserver) {
        Student student = studentRepository.getStudent(request.getValue());
        if (student == null) {
            LOGGER.debug("Error retrieving a student - student object is null");
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                .withDescription("Student with id " + request.getValue() + " not found")
                .asRuntimeException());
            return;
        }
        responseObserver.onNext(student);
        responseObserver.onCompleted();
    }
}

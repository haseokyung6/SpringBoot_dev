package com.basic.dev.service;

import com.basic.dev.dto.StudentDTO;
import com.basic.dev.entity.Student;
import com.basic.dev.entity.StudentDetail;
import com.basic.dev.repository.StudentDetailRepository;
import com.basic.dev.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentDetailRepository studentDetailRepository;

    @Override
    public List<StudentDTO.Response> getAllStudents() {
        return studentRepository.findAllWithDetail()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public StudentDTO.Response getStudentById(Long id) {
        Student student = studentRepository.findByIdWithStudentDetail(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다. id=" + id));

        return toResponse(student);
    }

    @Override
    public StudentDTO.Response getStudentByStudentNumber(String studentNumber) {
        Student student = studentRepository.findByStudentNumberWithDetail(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 학번의 학생이 없습니다. studentNumber=" + studentNumber));

        return toResponse(student);
    }

    @Override
    @Transactional
    public StudentDTO.Response createStudent(StudentDTO.Request request) {
        validateCreateRequest(request);

        Student student = Student.builder()
                .name(request.getName())
                .studentNumber(request.getStudentNumber())
                .build();

        if (request.getDetailRequest() != null) {
            StudentDetail detail = createDetailEntity(request.getDetailRequest());
            student.setStudentDetail(detail);
        }

        Student savedStudent = studentRepository.save(student);
        return toResponse(savedStudent);
    }

    @Override
    @Transactional
    public StudentDTO.Response updateStudent(Long id, StudentDTO.Request request) {
        Student student = studentRepository.findByIdWithStudentDetail(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다. id=" + id));

        validateUpdateRequest(id, request, student);

        student.setName(request.getName());
        student.setStudentNumber(request.getStudentNumber());

        StudentDTO.StudentDetailDTO detailRequest = request.getDetailRequest();

        if (detailRequest != null) {
            if (student.getStudentDetail() == null) {
                StudentDetail newDetail = createDetailEntity(detailRequest);
                student.setStudentDetail(newDetail);
            } else {
                StudentDetail detail = student.getStudentDetail();
                detail.setAddress(detailRequest.getAddress());
                detail.setPhoneNumber(detailRequest.getPhoneNumber());
                detail.setEmail(detailRequest.getEmail());
                detail.setDateOfBirth(detailRequest.getDateOfBirth());
            }
        }

        return toResponse(student);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findByIdWithStudentDetail(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다. id=" + id));

        studentRepository.delete(student);
    }

    private void validateCreateRequest(StudentDTO.Request request) {
        if (studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new IllegalArgumentException("이미 사용 중인 학번입니다.");
        }

        StudentDTO.StudentDetailDTO detailRequest = request.getDetailRequest();
        if (detailRequest != null) {
            if (detailRequest.getEmail() != null && studentDetailRepository.existsByEmail(detailRequest.getEmail())) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }

            if (detailRequest.getPhoneNumber() != null && studentDetailRepository.existsByPhoneNumber(detailRequest.getPhoneNumber())) {
                throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
            }
        }
    }

    private void validateUpdateRequest(Long id, StudentDTO.Request request, Student student) {
        if (studentRepository.existsByStudentNumberAndIdNot(request.getStudentNumber(), id)) {
            throw new IllegalArgumentException("이미 사용 중인 학번입니다.");
        }

        StudentDTO.StudentDetailDTO detailRequest = request.getDetailRequest();
        if (detailRequest == null) {
            return;
        }

        Long detailId = student.getStudentDetail() != null ? student.getStudentDetail().getId() : -1L;

        if (detailRequest.getEmail() != null && studentDetailRepository.existsByEmailAndIdNot(detailRequest.getEmail(), detailId)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (detailRequest.getPhoneNumber() != null && studentDetailRepository.existsByPhoneNumberAndIdNot(detailRequest.getPhoneNumber(), detailId)) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }
    }

    private StudentDetail createDetailEntity(StudentDTO.StudentDetailDTO dto) {
        return StudentDetail.builder()
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .dateOfBirth(dto.getDateOfBirth())
                .build();
    }

    private StudentDTO.Response toResponse(Student student) {
        StudentDTO.StudentDetailResponse detailResponse = null;

        if (student.getStudentDetail() != null) {
            StudentDetail detail = student.getStudentDetail();
            detailResponse = StudentDTO.StudentDetailResponse.builder()
                    .id(detail.getId())
                    .address(detail.getAddress())
                    .phoneNumber(detail.getPhoneNumber())
                    .email(detail.getEmail())
                    .dateOfBirth(detail.getDateOfBirth())
                    .build();
        }

        return StudentDTO.Response.builder()
                .id(student.getId())
                .name(student.getName())
                .studentNumber(student.getStudentNumber())
                .detailResponse(detailResponse)
                .build();
    }
}
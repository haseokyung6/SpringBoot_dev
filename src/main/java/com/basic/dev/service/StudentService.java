package com.basic.dev.service;

import com.basic.dev.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO.Response> getAllStudents();

    StudentDTO.Response getStudentById(Long id);

    StudentDTO.Response getStudentByStudentNumber(String studentNumber);

    StudentDTO.Response createStudent(StudentDTO.Request request);

    StudentDTO.Response updateStudent(Long id, StudentDTO.Request request);

    void deleteStudent(Long id);
}
package com.basic.dev.controller;

import com.basic.dev.dto.StudentDTO;
import com.basic.dev.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO.Response>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO.Response> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/number/{studentNumber}")
    public ResponseEntity<StudentDTO.Response> getStudentByStudentNumber(@PathVariable String studentNumber) {
        return ResponseEntity.ok(studentService.getStudentByStudentNumber(studentNumber));
    }

    @PostMapping
    public ResponseEntity<StudentDTO.Response> createStudent(@RequestBody StudentDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO.Response> updateStudent(@PathVariable Long id,
                                                             @RequestBody StudentDTO.Request request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
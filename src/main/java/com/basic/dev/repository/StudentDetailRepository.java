package com.basic.dev.repository;

import com.basic.dev.entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentDetailRepository extends JpaRepository<StudentDetail, Long> {

    Optional<StudentDetail> findByStudentId(Long studentId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}
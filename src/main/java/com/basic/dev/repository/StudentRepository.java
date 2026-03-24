package com.basic.dev.repository;

import com.basic.dev.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentNumber(String studentNumber);

    boolean existsByStudentNumber(String studentNumber);

    boolean existsByStudentNumberAndIdNot(String studentNumber, Long id);

    @Query("select s from Student s left join fetch s.studentDetail")
    List<Student> findAllWithDetail();

    @Query("select s from Student s left join fetch s.studentDetail where s.id = :id")
    Optional<Student> findByIdWithStudentDetail(Long id);

    @Query("select s from Student s left join fetch s.studentDetail where s.studentNumber = :studentNumber")
    Optional<Student> findByStudentNumberWithDetail(String studentNumber);

    @Query(value = """
        select s.name as name, s.student_number as studentNumber,
               sd.address as address, sd.email as email
        from students s
        join student_details sd on s.student_id = sd.student_id
        where s.student_id = :id
        """, nativeQuery = true)
    Object findStudentNativeById(Long id);
}
package com.basic.dev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "student_details",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_detail_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_student_detail_phone_number", columnNames = "phone_number"),
        @UniqueConstraint(name = "uk_student_detail_student_id", columnNames = "student_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_detail_id")
    private Long id;

    @Column(length = 255)
    private String address;

    @Column(name = "phone_number", unique = true, length = 30)
    private String phoneNumber;

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;

    public void setStudent(Student student) {
        this.student = student;

        if (student != null && student.getStudentDetail() != this) {
            student.setStudentDetail(this);
        }
    }
}
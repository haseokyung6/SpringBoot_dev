package com.basic.dev.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "student_number", nullable = false, unique = true, length = 30)
    private String studentNumber;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private StudentDetail studentDetail;

    public void setStudentDetail(StudentDetail studentDetail) {
        this.studentDetail = studentDetail;

        if (studentDetail != null && studentDetail.getStudent() != this) {
            studentDetail.setStudent(this);
        }
    }
}
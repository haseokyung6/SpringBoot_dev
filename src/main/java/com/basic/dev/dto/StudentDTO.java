package com.basic.dev.dto;

import lombok.*;

import java.time.LocalDate;

public class StudentDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String name;
        private String studentNumber;
        private StudentDetailDTO detailRequest;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StudentDetailDTO {
        private String address;
        private String phoneNumber;
        private String email;
        private LocalDate dateOfBirth;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String studentNumber;
        private StudentDetailResponse detailResponse;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StudentDetailResponse {
        private Long id;
        private String address;
        private String phoneNumber;
        private String email;
        private LocalDate dateOfBirth;
    }
}
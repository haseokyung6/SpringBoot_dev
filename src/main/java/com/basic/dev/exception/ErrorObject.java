package com.basic.dev.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorObject {

    private int status;
    private String message;
    private LocalDateTime timestamp;
}
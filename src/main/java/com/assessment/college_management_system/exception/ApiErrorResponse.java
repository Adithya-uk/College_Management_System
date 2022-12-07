package com.assessment.college_management_system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String message;

}

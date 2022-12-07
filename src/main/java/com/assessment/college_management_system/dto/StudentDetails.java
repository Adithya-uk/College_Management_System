package com.assessment.college_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetails {

    private String studentName;
    private long studentId;
    private String departmentName;
    private String facultyName;


}

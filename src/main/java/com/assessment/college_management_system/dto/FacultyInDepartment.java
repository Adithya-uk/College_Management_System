package com.assessment.college_management_system.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FacultyInDepartment {

    private String facultyName;
    private long facultyId;
    private String facultyDesignation;
    private String facultySubject;
    private String departmentName;


}

package com.assessment.college_management_system.controller;

import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.service.DepartmentService;
import com.assessment.college_management_system.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor

public class DepartmentController {

    private final DepartmentService departmentService;


    @GetMapping
    public List<Department> findAll(Pageable pageable) {
        return departmentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id) {
        return departmentService.findById(id);
    }

    @PostMapping
    public Department save(@RequestBody Department department) {
        return departmentService.save(department);
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable("id") Long id, @RequestBody Department department) {
        department.setId(id);
        return departmentService.update(department);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
    }


    @GetMapping("/hod")
    public List<FacultyInDepartment> findHod() {
        return departmentService.findHod();
    }


}

package com.assessment.college_management_system.controller;

import com.assessment.college_management_system.entity.Faculty;
import com.assessment.college_management_system.service.FacultyService;
import com.assessment.college_management_system.dto.FacultyCount;
import com.assessment.college_management_system.dto.FacultyInDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    public List<Faculty> findAll(Pageable pageable) {
        return facultyService.findAll(pageable);
    }


    @GetMapping("/{id}")
    public Faculty findById(@PathVariable("id") Long id) {
        return facultyService.findById(id);
    }


    @PostMapping
    public Faculty save(@RequestBody Faculty faculty) {
            return facultyService.save(faculty);
    }


    @PutMapping("/{id}")
    public Faculty update(@PathVariable("id") Long id, @RequestBody Faculty faculty) {
        faculty.setId(id);
        return facultyService.update(faculty);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        facultyService.delete(id);
    }

    @GetMapping("/department/{departmentName}")
    public List<FacultyInDepartment> getFacultyInDepartment(@PathVariable("departmentName") String departmentName) {
        return facultyService.getFacultyInDepartment(departmentName);
    }

    @GetMapping("/count")
    public List<FacultyCount> findFacultyCount(@RequestParam(defaultValue = "ALL") String designation) {
        return facultyService.findFacultyCount(designation);
    }
}

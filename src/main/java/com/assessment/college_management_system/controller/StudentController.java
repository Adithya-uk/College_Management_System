package com.assessment.college_management_system.controller;

import com.assessment.college_management_system.dto.DepartmentStudentCount;
import com.assessment.college_management_system.dto.StudentDetails;
import com.assessment.college_management_system.dto.StudentInDepartment;
import com.assessment.college_management_system.dto.StudentMark;
import com.assessment.college_management_system.service.StudentService;
import com.assessment.college_management_system.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> findAll(Pageable pageable) {
        return studentService.findAll(pageable);
    }


    @GetMapping("/{id}")
    public Student findById(@PathVariable("id") Long id) {
        return studentService.findById(id);
    }


    @PostMapping
    public Student save(@RequestBody Student student) {
       return studentService.save(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable("id") Long id, @RequestBody Student student) {
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {

        studentService.delete(id);
    }

    @GetMapping("/department/{departmentName}")
    public List<StudentInDepartment> findStudentInDepartment(@PathVariable("departmentName") String departmentName) {
        return studentService.findStudentInDepartment(departmentName);
    }

    @GetMapping("/details")//
    public List<StudentDetails> findStudentDetails() {
        return studentService.findStudentDetails();
    }

    @GetMapping("/count")
    public List<DepartmentStudentCount> findStudentCount(@RequestParam(defaultValue = "ALL") String departmentName) {
        return studentService.findStudentCount(departmentName);
    }

    @GetMapping("/topscorer")
    public List<StudentMark> findTopScorer() {
        return studentService.findTopScorer();
    }

}

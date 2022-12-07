package com.assessment.college_management_system.controller;

import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping
    public List<College> findAll(Pageable pageable) {
        return collegeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public College findById(@PathVariable("id") long id) {
        return collegeService.findById(id);
    }

    @PostMapping
    public College save(@RequestBody College college) {
        return collegeService.save(college);

    }

    @PutMapping("/{id}")
    public College update(@PathVariable("id") Long id, @RequestBody College college) {
        college.setId(id);
        return collegeService.update(college);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        collegeService.delete(id);
    }


}

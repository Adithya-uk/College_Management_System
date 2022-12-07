package com.assessment.college_management_system.service;

import com.assessment.college_management_system.repositories.FacultyRepository;
import com.assessment.college_management_system.dto.FacultyCount;
import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.Faculty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private static final String INVALID_ID_EXCEPTION_MESSAGE = "Invalid ID";

    public List<Faculty> findAll(Pageable pageable) {
        log.info("Getting details of all the faculties");
        Page<Faculty> pagingResult = facultyRepository.findAll(pageable);
        log.info("Returning details of all the faculties");
        return pagingResult.toList();
    }


    public Faculty findById(long id) {
        log.info("Searching for faculty with id=" + id);
        Optional<Faculty> result = facultyRepository.findById(id);
        if (result.isPresent()) {
            log.info("faculty with id=" + id + "found");
            return result.get();
        }
        log.error(INVALID_ID_EXCEPTION_MESSAGE);
        throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
    }


    public Faculty save(Faculty faculty) {

        log.info("Saving the faculty...");
        String designation = faculty.getDesignation();
        long id = faculty.getDepartment().getId();
        if(designation.equalsIgnoreCase("hod")) {
            Optional<FacultyInDepartment> rec = facultyRepository.findHodExists(designation, id);
            if (rec.isPresent()) {
                log.error("Hod already exists.Saving unsuccessful.");
                throw new RuntimeException("Hod exists already in the department");
            }
        }
        log.info("Saving successful.");
        return facultyRepository.save(faculty);

    }

    public Faculty update(Faculty faculty) {
        String designation = faculty.getDesignation();
        long id = faculty.getDepartment().getId();
        if(designation.equalsIgnoreCase("hod")) {
            Optional<FacultyInDepartment> rec = facultyRepository.findHodExists(designation, id);
            if (rec.isPresent()) {
                log.error("Hod already exists.Update unsuccessful.");
                throw new RuntimeException("Hod exists already in the department");
            }
        }
        log.info("Updating successful.");
        return facultyRepository.save(faculty);

    }

    public void delete(long id) {
        try {
            facultyRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error(INVALID_ID_EXCEPTION_MESSAGE);
            throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
        }
    }

    public List<FacultyInDepartment> getFacultyInDepartment(String name) {
        log.info("Getting details of faculty in department " + name);
        return facultyRepository.getFacultyInDepartment(name);
    }

    public List<FacultyCount> findFacultyCount(String designation) {
        log.info("Getting count of faculties under " + designation + " designation");
        return facultyRepository.findFacultyCount(designation);
    }
}

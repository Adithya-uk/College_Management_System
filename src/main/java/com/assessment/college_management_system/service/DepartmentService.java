package com.assessment.college_management_system.service;

import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.repositories.DepartmentRepository;
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
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private static final String INVALID_ID_EXCEPTION_MESSAGE = "Invalid ID";


    public List<Department> findAll(Pageable pageable) {
        log.info("Getting details of all the departments");
        Page<Department> pagingResult = departmentRepository.findAll(pageable);
        log.info("Returning details of all the departments");
        return pagingResult.getContent();
    }


    public Department findById(long id) {
        log.info("Searching for department with id=" + id);
        Optional<Department> result = departmentRepository.findById(id);
        if (result.isPresent()) {
            log.info("college with id=" + id + "found");
            return result.get();
        }
        log.error(INVALID_ID_EXCEPTION_MESSAGE);
        throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);

    }


    public Department save(Department department) {
        log.info("Saving the department...");
        try {
            int count = 0;
            int counter = 0;

            while (count != department.getFaculty().size()) {
                String designation = department.getFaculty().get(count).getDesignation();
                if (designation.equalsIgnoreCase("hod")) {
                    counter++;
                }
                count++;
            }
            if (counter > 1) {
                throw new RuntimeException();
            }
            return departmentRepository.save(department);
        } catch (Exception e) {
            throw new RuntimeException("Saving unsuccessful");
        }
    }

    public Department update(Department department) {
            log.info("Updating the department");
            long id = department.getId();
            int count = 0;
            while (count != department.getFaculty().size()) {
                String designation = department.getFaculty().get(count).getDesignation();
                if (designation.equalsIgnoreCase("hod")) {
                    Optional<FacultyInDepartment> rec = departmentRepository.findHodExists(designation, id);
                    if (rec.isPresent()) {
                        log.error("Hod already exists.Update unsuccessful.");
                        throw new RuntimeException("Hod exists already in the department");
                    }
                }
                count++;
            }
            log.info("Updating successful.");
            return departmentRepository.save(department);
    }


    public void delete(long id) {
        try {
            departmentRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error(INVALID_ID_EXCEPTION_MESSAGE);
            throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
        }
    }

    public List<FacultyInDepartment> findHod() {
        log.info("Getting details of all the hods");
        return departmentRepository.findHod();
    }


}

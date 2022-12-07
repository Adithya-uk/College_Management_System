package com.assessment.college_management_system;

import com.assessment.college_management_system.dto.DepartmentStudentCount;
import com.assessment.college_management_system.dto.FacultyCount;
import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.entity.Faculty;
import com.assessment.college_management_system.repositories.DepartmentRepository;
import com.assessment.college_management_system.repositories.FacultyRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacultyRepositoryIT {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void givenFacultyDetails_whenFacultySave_thenFacultyPersists() {
        long id = 1;
//         faculty = facultyRepository.findById(id).get();
        Department department = departmentRepository.findById(id).get();
        var faculty = Faculty.builder()
                .name("ranjith")
                .designation("prof")
                .subject("oops")
                .department(department)
                .build();

        Faculty returnedFaculty = facultyRepository.save(faculty);
        Assertions.assertNotNull(returnedFaculty);
        Assertions.assertEquals(faculty.getName(), returnedFaculty.getName());

    }


    @Test
    public void givenFacultyList_whenFindAllFaculty_thenAllFacultyDetailsReturned() {

        List<Faculty> facultyList = facultyRepository.findAll();
        Assertions.assertNotNull(facultyList);
        Assertions.assertEquals(facultyRepository.count(), facultyList.size());
    }

    @Test
    public void givenId_whenIdPresent_thenFacultyDetailsFound() {

        long id = 1;
        Optional<Faculty> faculty = facultyRepository.findById(id);

        Assertions.assertNotNull(faculty);
        Assertions.assertEquals(id, faculty.get().getId());

    }

    @Test
    public void givenId_whenIdNotPresent_thenFacultyDetailsNotFound() {

        long id = 10;
        Optional<Faculty> faculty = facultyRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), faculty);
    }

    @Test
    public void givenUpdatedDetails_whenFacultyUpdate_UpdatedDataPersists() {

        long id = 1;
        Department department = departmentRepository.findById(id).get();
        var faculty = Faculty.builder()
                .id(1)
                .name("ranjith")
                .designation("prof")
                .subject("oops")
                .department(department)
                .build();

        Faculty returnedFaculty = facultyRepository.save(faculty);
        Assertions.assertNotNull(returnedFaculty);
        Assertions.assertEquals(faculty.getName(), returnedFaculty.getName());

    }

    @Test
    public void givenFacultyId_whenPresent_deleteIsPerformed() {
        long id = 1;
        facultyRepository.deleteById(id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), faculty);

    }


    @Test
    public void givenFacultyId_whenNotPresent_deleteIsNotPerformed() {
        long id = 10;
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            facultyRepository.deleteById(id);
        });
    }


    @Test
    public void givenDepartmentName_whenDepartmentExists_FacultyInDepartmentReturned(){
        List<FacultyInDepartment> facultyList=facultyRepository.getFacultyInDepartment("cs");
        Assertions.assertEquals("cs",facultyList.get(0).getDepartmentName());

    }

    @Test
    public void givenDepartmentName_whenDepartmentNotPresent_FacultyInDepartmentReturned(){
        List<FacultyInDepartment> facultyList=facultyRepository.getFacultyInDepartment("environmental");
        Assertions.assertEquals(0,facultyList.size());

    }

    @Test
    public void givenAllDesignation_whenFindFacultyCount_thenCountOfFacultyReturned() {
        List<FacultyCount> facultyList=facultyRepository.findFacultyCount("ALL");
        Assertions.assertTrue(facultyList.size()>0);
    }

    @Test
    public void givenParticularDesignation_whenFindFacultyCount_thenCountOfFacultyReturned() {
        List<FacultyCount> facultyList=facultyRepository.findFacultyCount("prof");
        Assertions.assertTrue(facultyList.size()>0);
    }


}

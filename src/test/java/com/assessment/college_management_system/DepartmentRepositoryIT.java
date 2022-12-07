package com.assessment.college_management_system;

import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.repositories.CollegeRepository;
import com.assessment.college_management_system.repositories.DepartmentRepository;
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
public class DepartmentRepositoryIT {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CollegeRepository collegeRepository;

    @Test
    public void givenDepartmentDetails_whenDepartmentSave_thenDepartmentPersists() {
        long id = 1;
        College college = collegeRepository.findById(id).get();
        var department = Department.builder()
                .name("biotech")
                .college(college)
                .build();
        Department returnedDepartment = departmentRepository.save(department);
        Assertions.assertNotNull(returnedDepartment);
        Assertions.assertEquals(department.getName(), returnedDepartment.getName());

    }

    @Test
    public void givenDepartmentDetails_whenDuplicateDepartmentSave_thenThrowsError() {
        long id = 1;
        College college = collegeRepository.findById(id).get();
        var department = Department.builder()
                .name("cs")
                .college(college)
                .build();
        Assertions.assertThrows(Exception.class, () -> {
            departmentRepository.save(department);
        });

    }

    @Test
    public void givenDepartmentList_whenFindAllDepartment_thenAllDepartmentDetailsReturned() {

        List<Department> departmentList = departmentRepository.findAll();
        Assertions.assertNotNull(departmentList);
        Assertions.assertEquals(departmentRepository.count(), departmentList.size());
    }

    @Test
    public void givenId_whenIdPresent_thenCollegeDetailsFound() {

        long id = 1;
        Optional<Department> department = departmentRepository.findById(id);

        Assertions.assertNotNull(department);
        Assertions.assertEquals(id, department.get().getId());

    }

    @Test
    public void givenId_whenIdNotPresent_thenDepartmentDetailsNotFound() {

        long id = 10;
        Optional<Department> department = departmentRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), department);
    }


    @Test
    public void givenUpdatedDetails_whenDepartmentUpdate_UpdatedDataPersists() {
        long id = 1;
        College college = collegeRepository.findById(id).get();
        var department = Department.builder()
                .id(10)
                .name("biotech")
                .college(college)
                .build();
        Department returnedDepartment = departmentRepository.save(department);
        //long returnedId=returnedDepartment.getId();
        var departmentNew = Department.builder()
                .id(10)
                .name("chem")
                .college(college)
                .build();
        returnedDepartment = departmentRepository.save(departmentNew);
        Assertions.assertNotNull(returnedDepartment);
        Assertions.assertEquals(departmentNew.getName(), returnedDepartment.getName());

    }

    @Test
    public void givenDepartmentId_whenPresent_deleteIsPerformed() {
        long id = 1;
        departmentRepository.deleteById(id);
        Optional<Department> department = departmentRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), department);

    }


    @Test
    public void givenDepartmentId_whenNotPresent_deleteIsNotPerformed() {
        long id = 10;
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            departmentRepository.deleteById(id);
        });
    }


    @Test
    public void givenDepartmentDetails_whenFindAllHod_allHodsReturned() {
        List<FacultyInDepartment> departmentHods = departmentRepository.findHod();
        Assertions.assertEquals(departmentHods.get(0).getFacultyDesignation(), "hod");
    }

}

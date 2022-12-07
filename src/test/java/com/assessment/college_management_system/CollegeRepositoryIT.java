package com.assessment.college_management_system;

import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.repositories.CollegeRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollegeRepositoryIT {

    @Autowired
    CollegeRepository collegeRepository;


    @Test
    public void givenCollegeDetails_whenCollegeSave_thenCollegePersists() {

        var college = College.builder()
                .phoneNumber("767777")
                .name("sctce")
                .build();

        College returnedCollege = collegeRepository.save(college);
        Assertions.assertNotNull(returnedCollege);
        Assertions.assertEquals(college.getName(), returnedCollege.getName());

    }

    @Test
    public void givenCollegeAndDepartmentDetails_whenCollegeSave_thenCollegePersists() {
        var department = Department.builder()
                .name("cs").build();
        var departmentTwo = Department.builder()
                .name("mech")
                .build();

        List<Department> listDepartment = new ArrayList<>();
        listDepartment.add(department);
        listDepartment.add(departmentTwo);

        var college = College.builder()
                .phoneNumber("767777")
                .name("sctce")
                .build();
        listDepartment.forEach(dept -> dept.setCollege(college));
        college.setDepartment(listDepartment);
        // college.getDepartment().addAll(listDepartment);

        College returnedCollege = collegeRepository.save(college);
        Assertions.assertNotNull(returnedCollege);
        Assertions.assertEquals(college.getName(), returnedCollege.getName());
    }


    @Test
    public void givenCollegeDetails_whenDepartmentAlreadyExists_thenThrowException() {
        var department = Department.builder().name("cs").build();
        var departmentTwo = Department.builder().name("cs").build();

        List<Department> listDepartment = new ArrayList<>();
        listDepartment.add(department);
        listDepartment.add(departmentTwo);

        var college = College.builder()
                .phoneNumber("767777")
                .name("sctce")
                .build();
        listDepartment.forEach(dept -> dept.setCollege(college));
        college.setDepartment(listDepartment);
//        college.getDepartment().addAll(listDepartment);

        Assertions.assertThrows(Exception.class, () -> {
            collegeRepository.save(college);
        });

    }

    @Test
    public void givenCollegesList_whenFindAllColleges_thenAllCollegesDetailsReturned() {

        List<College> collegesList = collegeRepository.findAll();
        Assertions.assertNotNull(collegesList);
        Assertions.assertEquals(collegeRepository.count(), collegesList.size());
    }

    @Test
    public void givenId_whenIdPresent_thenCollegeDetailsFound() {

        long id = 1;
        Optional<College> college = collegeRepository.findById(id);

        Assertions.assertNotNull(college);
        Assertions.assertEquals(id, college.get().getId());

    }

    @Test
    public void givenId_whenIdNotPresent_thenCollegeDetailsNotFound() {

        long id = 10;
        Optional<College> college = collegeRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), college);
    }

    @Test
    public void givenUpdatedDetails_whenCollegeUpdate_UpdatedDataPersists() {

        Department department = new Department();
        List<Department> list = new ArrayList<>();
        list.add(department);
        var college = College.builder()
                .id(10)
                .phoneNumber("767777")
                .name("sctce")
                .department(list)
                .build();
        College returnedCollege = collegeRepository.save(college);
        var collegeNew = College.builder()
                .id(10)
                .phoneNumber("213123")
                .name("xyz")
                .department(list)
                .build();
        returnedCollege = collegeRepository.save(collegeNew);
        Assertions.assertNotNull(returnedCollege);
        Assertions.assertEquals(collegeNew.getName(), returnedCollege.getName());

    }

    @Test
    public void givenCollegeId_whenPresent_deleteIsPerformed() {
        long id = 1;
        collegeRepository.deleteById(id);
        Optional<College> college = collegeRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), college);

    }


    @Test
    public void givenCollegeId_whenNotPresent_deleteIsNotPerformed() {
        long id = 10;
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            collegeRepository.deleteById(id);
        });

    }


}

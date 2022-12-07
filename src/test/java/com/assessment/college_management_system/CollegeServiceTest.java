package com.assessment.college_management_system;

import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.repositories.CollegeRepository;
import com.assessment.college_management_system.service.CollegeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollegeServiceTest {

    @Mock
    CollegeRepository collegeRepository;

    @InjectMocks
    CollegeService collegeService;


    @Test
    public void givenCollegesList_whenFindAllColleges_thenAllCollegesDetailsReturned() {

        College college = new College();
        List<College> list = new ArrayList<>();
        list.add(college);
        Page<College> pagingResult = new PageImpl<College>(list);
        Pageable paging = PageRequest.of(0, 5);

        when(collegeRepository.findAll(paging)).thenReturn(pagingResult);
        var collegeReturned = collegeService.findAll(paging);
        Assertions.assertEquals(1, collegeReturned.size());
    }


    @Test
    public void givenId_whenIdPresent_thenCollegeDetailsFound() {
        College college = new College();
        when(collegeRepository.findById((long) 1)).thenReturn(Optional.of(college));
        College collegeReturned = collegeService.findById(1);
        Assertions.assertNotNull(collegeReturned);

    }

    @Test
    public void givenId_whenIdNotPresent_thenCollegeDetailsNotFound() {
        College college = new College();
        when(collegeRepository.findById((long)10)).thenThrow(new RuntimeException("Invalid Id"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            collegeService.findById(10);
        });


    }


    @Test
    public void givenCollegeDetails_whenCollegeSave_thenCollegeReturned() {

        var college = College.builder()
                .phoneNumber("767777")
                .name("sctce")
                .build();
        when(collegeRepository.save(college)).thenReturn(college);
        assertEquals(college, collegeService.save(college));
    }

    @Test
    public void givenCollegeAndDepartmentDetails_whenCollegeSave_thenSavedCollegeReturned() {
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
                .department(listDepartment)
                .build();

        when(collegeRepository.save(college)).thenReturn(college);
        assertEquals(college, collegeService.save(college));
    }

    @Test
    public void givenCollegeAndDepartmentDetails_whenDuplicateDepartmentSave_thenExceptionThrown() {
        var department = Department.builder()
                .name("cs").build();
        var departmentTwo = Department.builder()
                .name("cs")
                .build();

        List<Department> listDepartment = new ArrayList<>();
        listDepartment.add(department);
        listDepartment.add(departmentTwo);

        var college = College.builder()
                .phoneNumber("767777")
                .name("sctce")
                .department(listDepartment)
                .build();

        when(collegeRepository.save(college)).thenThrow(new RuntimeException("Duplicate department"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            collegeService.save(college);
        });
    }


    @Test
    public void givenUpdatedDetails_whenCollegeUpdate_UpdatedDataPersists() {
        var college = College.builder()
                .id(1)
                .phoneNumber("767777")
                .name("sctce")
                .build();
        when(collegeRepository.save(college)).thenReturn(college);
        College returnedCollege = collegeService.update(college);
        assertEquals(college, returnedCollege);
    }

    @Test
    public void givenValidCollegeId_whenDeletingCollege_thenDeleteMethodIsInvoked() {
        doNothing().when(collegeRepository).deleteById((long) 10);
        collegeService.delete(10);
        verify(collegeRepository, times(1)).deleteById((long) 10);
    }

    @Test
    public void givenInvalidCollegeId_whenDeletingCollege_exceptionIsThrown() {
        doThrow(RuntimeException.class).when(collegeRepository).deleteById((long) 100);
        Assertions.assertThrows(RuntimeException.class, () -> {
            collegeService.delete(100);
        });
    }



}

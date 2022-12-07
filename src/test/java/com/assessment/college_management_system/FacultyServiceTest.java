package com.assessment.college_management_system;

import com.assessment.college_management_system.dto.FacultyCount;
import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.entity.Faculty;
import com.assessment.college_management_system.repositories.DepartmentRepository;
import com.assessment.college_management_system.repositories.FacultyRepository;
import com.assessment.college_management_system.service.DepartmentService;
import com.assessment.college_management_system.service.FacultyService;
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
public class FacultyServiceTest {

    @Mock
    FacultyRepository facultyRepository;

    @InjectMocks
    FacultyService facultyService;


    @Test
    public void givenFacultyList_whenFindAllFaculty_thenAllFacultyDetailsReturned() {

        Faculty faculty = new Faculty();
        List<Faculty> list = new ArrayList<>();
        list.add(faculty);
        Page<Faculty> pagingResult = new PageImpl<Faculty>(list);
        Pageable paging = PageRequest.of(0, 5);

        when(facultyRepository.findAll(paging)).thenReturn(pagingResult);
        List<Faculty> returnedFaculty = facultyService.findAll(paging);
        Assertions.assertEquals(1, returnedFaculty.size());
    }

    @Test
    public void givenId_whenIdPresent_thenFacultyDetailsFound() {
        Faculty faculty = new Faculty();
        when(facultyRepository.findById((long) 1)).thenReturn(Optional.of(faculty));
        Faculty facultyReturned = facultyService.findById(1);
        Assertions.assertNotNull(facultyReturned);

    }


    @Test
    public void givenId_whenIdNotPresent_thenFacultyDetailsNotFound() {
        when(facultyRepository.findById((long)10)).thenThrow(new RuntimeException("Invalid Id"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            facultyRepository.findById((long)10);
        });
    }

    @Test
    public void givenFacultyDetails_whenFacultySave_savedFacultyReturned(){
        var department = Department.builder().build();
        var faculty = Faculty
                .builder()
                .name("akash")
                .designation("hod")
                .department(department)
                .build();

        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Assertions.assertEquals(faculty,facultyService.save(faculty));

    }

    @Test
    public void givenUpdatedDetails_whenFacultyUpdate_UpdatedDataPersists() {
        var department = Department.builder().build();
        var faculty = Faculty
                .builder()
                .name("akash")
                .designation("hod")
                .department(department)
                .build();

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty returnedFaculty = facultyService.update(faculty);
        assertEquals(faculty, returnedFaculty);
    }


    @Test
    public void givenValidFacultyId_whenDeletingFaculty_thenDeleteMethodIsInvoked() {
        doNothing().when(facultyRepository).deleteById((long) 10);
        facultyService.delete(10);
        verify(facultyRepository, times(1)).deleteById((long) 10);
    }

    @Test
    public void givenInvalidFacultyId_whenDeletingFaculty_thenExceptionIsThrown() {
        doThrow(RuntimeException.class).when(facultyRepository).deleteById((long) 100);
        Assertions.assertThrows(RuntimeException.class, () -> {
            facultyService.delete(100);
        });
    }

    @Test
    public  void givenDepartmentId_whenFindFacultyInDepartment_thenFacultyInDepartmentReturned(){
        FacultyInDepartment faculty = new FacultyInDepartment();
        List<FacultyInDepartment> list = new ArrayList<>();
        list.add(faculty);
        when(facultyRepository.getFacultyInDepartment("cs")).thenReturn(list);
        Assertions.assertTrue(facultyService.getFacultyInDepartment("cs").size()==1);
    }


    @Test
    public  void givenDesignation_whenDesignationExist_thenFacultyCountReturned(){
        FacultyCount faculty = new FacultyCount();
        List<FacultyCount> list = new ArrayList<>();
        list.add(faculty);
        when(facultyRepository.findFacultyCount("prof")).thenReturn(list);
        Assertions.assertTrue(facultyService.findFacultyCount("prof").size()==1);
    }


    @Test
    public void givenDesignation_whenDesignationDoesNotExist_thenExceptionThrown() {
        doThrow(RuntimeException.class).when(facultyRepository).findFacultyCount("x");
        Assertions.assertThrows(RuntimeException.class, () -> {
            facultyService.findFacultyCount("x");
        });
    }


}

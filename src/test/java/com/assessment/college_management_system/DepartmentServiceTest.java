package com.assessment.college_management_system;

import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.repositories.DepartmentRepository;
import com.assessment.college_management_system.service.DepartmentService;
import com.assessment.college_management_system.entity.Faculty;
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
public class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentService departmentService;

    @Test
    public void givenDepartmentList_whenFindAllDepartment_thenAllDepartmentDetailsReturned() {

        Department department = new Department();
        List<Department> list = new ArrayList<>();
        list.add(department);
        Page<Department> pagingResult = new PageImpl<Department>(list);
        Pageable paging = PageRequest.of(0, 5);

        when(departmentRepository.findAll(paging)).thenReturn(pagingResult);
        List<Department> returnedDepartment = departmentService.findAll(paging);
        Assertions.assertEquals(1, returnedDepartment.size());
    }

    @Test
    public void givenId_whenIdPresent_thenDepartmentDetailsFound() {
        Department department = new Department();
        when(departmentRepository.findById((long) 1)).thenReturn(Optional.of(department));
        Department departmentReturned = departmentService.findById(1);
        Assertions.assertNotNull(departmentReturned);

    }


    @Test
    public void givenId_whenIdNotPresent_thenExceptionThrown() {
        when(departmentRepository.findById((long)10)).thenThrow(new RuntimeException("Invalid Id"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            departmentRepository.findById((long)10);
        });
    }

    @Test
    public void givenDepartmentDetails_whenDepartmentSave_savedDepartmentReturned(){
        var faculty = Faculty
                .builder().name("akash").designation("hod")
                .build();
        List<Faculty> list = new ArrayList<>();
        list.add(faculty);
        var departmentNew = Department.builder()
                .name("biotech")
                .faculty(list)
                .build();
        when(departmentRepository.save(departmentNew)).thenReturn(departmentNew);
        Assertions.assertEquals(departmentNew,departmentService.save(departmentNew));

    }

    @Test
    public void givenUpdatedDetails_whenDepartmentUpdate_UpdatedDataPersists() {
        var faculty = Faculty
                .builder().name("akash").designation("hod")
                .build();
        List<Faculty> list = new ArrayList<>();
        list.add(faculty);
        var department = Department.builder()
                .name("biotech")
                .faculty(list)
                .build();

        when(departmentRepository.save(department)).thenReturn(department);
        Department returnedDepartment = departmentService.update(department);
        assertEquals(department, returnedDepartment);
    }


    @Test
    public void givenValidDepartmentId_whenDeletingDepartment_thenDeleteMethodIsInvoked() {
        doNothing().when(departmentRepository).deleteById((long) 10);
        departmentService.delete(10);
        verify(departmentRepository, times(1)).deleteById((long) 10);
    }

    @Test
    public void givenInvalidDepartmentId_whenDeletingDepartment_thenExceptionIsThrown() {
        doThrow(RuntimeException.class).when(departmentRepository).deleteById((long) 100);
        Assertions.assertThrows(RuntimeException.class, () -> {
            departmentService.delete(100);
        });
    }

    @Test
    public void givenFacultyList_whenFindHod_returnHodOfAllDepartments(){
        FacultyInDepartment faculty = new FacultyInDepartment();
        List<FacultyInDepartment> list = new ArrayList<>();
        list.add(faculty);
        when(departmentRepository.findHod()).thenReturn(list);
        Assertions.assertTrue(departmentService.findHod().size()==1);

    }



}

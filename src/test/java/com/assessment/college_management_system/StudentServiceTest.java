package com.assessment.college_management_system;

import com.assessment.college_management_system.dto.*;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.entity.Faculty;
import com.assessment.college_management_system.entity.Student;
import com.assessment.college_management_system.repositories.FacultyRepository;
import com.assessment.college_management_system.repositories.StudentRepository;
import com.assessment.college_management_system.service.FacultyService;
import com.assessment.college_management_system.service.StudentService;
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
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    public void givenStudentList_whenFindAllStudent_thenAllStudentDetailsReturned() {

        Student student = new Student();
        List<Student> list = new ArrayList<>();
        list.add(student);
        Page<Student> pagingResult = new PageImpl<Student>(list);
        Pageable paging = PageRequest.of(0, 5);

        when(studentRepository.findAll(paging)).thenReturn(pagingResult);
        List<Student> returnedStudent = studentService.findAll(paging);
        Assertions.assertEquals(1, returnedStudent.size());
    }

    @Test
    public void givenId_whenIdPresent_thenStudentDetailsFound() {
        Student student = new Student();
        when(studentRepository.findById((long) 1)).thenReturn(Optional.of(student));
        Student studentReturned = studentService.findById(1);
        Assertions.assertNotNull(studentReturned);

    }


    @Test
    public void givenId_whenIdNotPresent_thenStudentDetailsNotFound() {
        when(studentRepository.findById((long)10)).thenThrow(new RuntimeException("Invalid Id"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            studentRepository.findById((long)10);
        });
    }

    @Test
    public void givenStudentDetails_whenStudentSave_savedStudentReturned(){
        var department = Department.builder().build();
        var student = Student
                .builder()
                .name("akash")
                .department(department)
                .build();

        when(studentRepository.save(student)).thenReturn(student);

        Assertions.assertEquals(student,studentService.save(student));

    }

    @Test
    public void givenUpdatedDetails_whenStudentUpdate_UpdatedDataPersists() {
        var department = Department.builder().build();
        var student = Student
                .builder()
                .name("akash")
                .department(department)
                .build();

        when(studentRepository.save(student)).thenReturn(student);
        Student returnedStudent = studentService.update(student);
        assertEquals(student, returnedStudent);
    }


    @Test
    public void givenValidStudentId_whenDeletingStudent_thenDeleteMethodIsInvoked() {
        doNothing().when(studentRepository).deleteById((long) 10);
        studentService.delete(10);
        verify(studentRepository, times(1)).deleteById((long) 10);
    }

    @Test
    public void givenInvalidStudentId_whenDeletingStudent_thenExceptionIsThrown() {
        doThrow(RuntimeException.class).when(studentRepository).deleteById((long) 100);
        Assertions.assertThrows(RuntimeException.class, () -> {
            studentService.delete(100);
        });
    }


    @Test
    public  void givenDepartmentId_whenFindFacultyInDepartment_thenStudentInDepartmentReturned(){
        StudentInDepartment student = new StudentInDepartment();
        List<StudentInDepartment> list = new ArrayList<>();
        list.add(student);
        when(studentRepository.findStudentInDepartment("cs")).thenReturn(list);
        Assertions.assertTrue(studentService.findStudentInDepartment("cs").size()==1);
    }


    @Test
    public void givenDepartment_whenDepartmentExist_thenStudentCountReturned(){
        DepartmentStudentCount student = new DepartmentStudentCount();
        List<DepartmentStudentCount> list = new ArrayList<>();
        list.add(student);
        when(studentRepository.findStudentCount("cs")).thenReturn(list);
        Assertions.assertTrue(studentService.findStudentCount("cs").size()==1);
    }


    @Test
    public void givenDepartment_whenDepartmentDoesNotExist_thenExceptionThrown() {
        doThrow(RuntimeException.class).when(studentRepository).findStudentCount("x");
        Assertions.assertThrows(RuntimeException.class, () -> {
            studentService.findStudentCount("x");
        });
    }

    @Test
    public void givenStudentList_whenFindStudentDetails_thenStudentDetailsReturned(){
        StudentDetails student = new StudentDetails();
        List<StudentDetails> list = new ArrayList<>();
        list.add(student);
        when(studentRepository.findStudentDetails()).thenReturn(list);
        Assertions.assertTrue(studentService.findStudentDetails().size()==1);
    }



}

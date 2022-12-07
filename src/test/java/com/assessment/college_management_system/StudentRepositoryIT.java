package com.assessment.college_management_system;

import com.assessment.college_management_system.dto.DepartmentStudentCount;
import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.dto.StudentDetails;
import com.assessment.college_management_system.dto.StudentInDepartment;
import com.assessment.college_management_system.entity.Department;
import com.assessment.college_management_system.entity.Faculty;
import com.assessment.college_management_system.entity.Student;
import com.assessment.college_management_system.repositories.DepartmentRepository;
import com.assessment.college_management_system.repositories.StudentRepository;
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
public class StudentRepositoryIT {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void givenStudentDetails_whenStudentSave_thenStudentPersists() {
        long id = 1;
//         faculty = facultyRepository.findById(id).get();
        Department department = departmentRepository.findById(id).get();
        var student = Student.builder()
                .name("akshara")
                .department(department)
                .build();

        Student returnedStudent = studentRepository.save(student);
        Assertions.assertNotNull(returnedStudent);
        Assertions.assertEquals(student.getName(), returnedStudent.getName());

    }


    @Test
    public void givenStudentList_whenFindAllStudent_thenAllStudentDetailsReturned() {

        List<Student> studentList = studentRepository.findAll();
        Assertions.assertNotNull(studentList);
        Assertions.assertEquals(studentRepository.count(), studentList.size());
    }

    @Test
    public void givenId_whenIdPresent_thenStudentDetailsFound() {

        long id = 1;
        Optional<Student> student = studentRepository.findById(id);

        Assertions.assertNotNull(student);
        Assertions.assertEquals(id, student.get().getId());

    }

    @Test
    public void givenId_whenIdNotPresent_thenStudentDetailsNotFound() {

        long id = 10;
        Optional<Student> student = studentRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), student);
    }

    @Test
    public void givenUpdatedDetails_whenStudentUpdate_UpdatedDataPersists() {

        long id = 1;
        Department department = departmentRepository.findById(id).get();
        var student = Student.builder()
                .name("akshara")
                .department(department)
                .build();

        Student returnedStudent = studentRepository.save(student);
        Assertions.assertNotNull(returnedStudent);
        Assertions.assertEquals(student.getName(), returnedStudent.getName());

    }

    @Test
    public void givenFacultyId_whenPresent_deleteIsPerformed() {
        long id = 1;
        studentRepository.deleteById(id);
        Optional<Student> student = studentRepository.findById(id);
        Assertions.assertEquals(Optional.empty(), student);

    }


    @Test
    public void givenFacultyId_whenNotPresent_deleteIsNotPerformed() {
        long id = 10;
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            studentRepository.deleteById(id);
        });
    }

    @Test
    public void givenDepartmentName_whenDepartmentExists_FacultyInDepartmentReturned(){
        List<StudentInDepartment> studentList=studentRepository.findStudentInDepartment("cs");
        Assertions.assertEquals("cs",studentList.get(0).getDepartmentName());

    }

    @Test
    public void givenDepartmentName_whenDepartmentNotPresent_FacultyInDepartmentReturned(){
        List<StudentInDepartment> studentList=studentRepository.findStudentInDepartment("environmental");
        Assertions.assertEquals(0,studentList.size());
    }

    @Test
    public void givenAllDepartment_whenFindStudentCount_CountOfStudentReturned() {
        List<DepartmentStudentCount> studentList=studentRepository.findStudentCount("ALL");
        Assertions.assertTrue(studentList.size()>0);
    }

    @Test
    public void givenParticularDepartment_whenFindStudentCount_CountOfStudentReturned() {
        List<DepartmentStudentCount> studentList=studentRepository.findStudentCount("cs");
        Assertions.assertTrue(studentList.size()>0);
    }

    @Test
    public void givenFindStudentDetails_whenFindStudentDetails_StudentDetailsReturned() {
        List<StudentDetails> studentList=studentRepository.findStudentDetails();
        Assertions.assertTrue(studentList.size()>0);
    }

}

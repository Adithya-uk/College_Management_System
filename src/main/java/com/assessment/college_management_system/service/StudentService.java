package com.assessment.college_management_system.service;

import com.assessment.college_management_system.dto.DepartmentStudentCount;
import com.assessment.college_management_system.dto.StudentDetails;
import com.assessment.college_management_system.dto.StudentInDepartment;
import com.assessment.college_management_system.dto.StudentMark;
import com.assessment.college_management_system.entity.Student;
import com.assessment.college_management_system.repositories.StudentRepository;
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
public class StudentService {

    private final StudentRepository studentRepository;

    private static final String INVALID_ID_EXCEPTION_MESSAGE = "Invalid ID";

    public List<Student> findAll(Pageable pageable) {
        log.info("Getting details of all the students");
        Page<Student> pagingResult = studentRepository.findAll(pageable);
        log.info("Returning details of all the students");
        return pagingResult.toList();
    }


    public Student findById(long id) {
        log.info("Searching for student with id=" + id);
        Optional<Student> result = studentRepository.findById(id);
        if (result.isPresent()) {
            log.info("student with id=" + id + "found");
            return result.get();
        }
        log.error(INVALID_ID_EXCEPTION_MESSAGE);
        throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
    }


    public Student save(Student student) {
        log.info(("Saving student"));
        return studentRepository.save(student);

    }

    public Student update(Student student) {
        log.info(("Updating student"));
        try {
            return studentRepository.save(student);
        } catch (RuntimeException e) {
            log.info("update unsuccessful");
            log.error(String.valueOf(e));
            throw new RuntimeException("update unsuccessful");
        }

    }


    public void delete(long id) {

        try {
            studentRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error(INVALID_ID_EXCEPTION_MESSAGE);
            throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
        }
    }

    public List<StudentInDepartment> findStudentInDepartment(String name) {
        log.info("Getting details of students in department " + name);
        return studentRepository.findStudentInDepartment(name);

    }

    public List<StudentDetails> findStudentDetails() {
        log.info("Getting details of students along with their department name and hod name");
        return studentRepository.findStudentDetails();
    }

    public List<DepartmentStudentCount> findStudentCount(String name) {
        log.info("Getting count of students in " + name + " department");
        return studentRepository.findStudentCount(name);
    }

    public List<StudentMark> findTopScorer (){
        log.info("Getting the top scorer");
        return studentRepository.findTopScorer();
    }

}

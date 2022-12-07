package com.assessment.college_management_system.repositories;

import com.assessment.college_management_system.dto.DepartmentStudentCount;
import com.assessment.college_management_system.dto.StudentDetails;
import com.assessment.college_management_system.dto.StudentInDepartment;
import com.assessment.college_management_system.dto.StudentMark;
import com.assessment.college_management_system.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT new com.assessment.college_management_system.dto.StudentInDepartment(s.name,s.id,d.name) FROM Department d JOIN d.student s WHERE d.name=:name")
    public List<StudentInDepartment> findStudentInDepartment(String name);

    @Query("SELECT new com.assessment.college_management_system.dto.StudentDetails(s.name,s.id,d.name,f.name)FROM Department d JOIN d.student s JOIN d.faculty f WHERE f.designation='hod'")
    public List<StudentDetails> findStudentDetails();

    @Query("SELECT new com.assessment.college_management_system.dto.DepartmentStudentCount(d.name,count(s.name)) FROM Department d JOIN d.student s WHERE d.name=:name OR (d.name LIKE '%' AND :name='ALL') GROUP BY(d.name) ")
    public List<DepartmentStudentCount> findStudentCount(String name);

//    @Query("SELECT new com.assessment.college_management_system.dto.StudentMark(s.name,MAX(s.mark1+s.mark2+s.mark3),d.name) FROM Department d JOIN d.student s GROUP BY d.name ")
//    public List<StudentMark> findTopScorer();

//    @Query("SELECT new com.assessment.college_management_system.dto.StudentMark(MAX(s.mark1+s.mark2+s.mark3),d.name) FROM Department d JOIN d.student s GROUP BY d.name")
//    public List<StudentMark> findTopScorer();


    @Query("SELECT new com.assessment.college_management_system.dto.StudentMark(s1.name,d1.name as dept_name,(s1.mark1+s1.mark2+s1.mark3) AS total_mark) FROM Department d1 JOIN d1.student s1 WHERE (s1.mark1+s1.mark2+s1.mark3) =(SELECT Max(s2.mark1+s2.mark2+s2.mark3) FROM Department d2 JOIN d2.student s2 WHERE d2.id = d1.id)")
    public List<StudentMark> findTopScorer();

}

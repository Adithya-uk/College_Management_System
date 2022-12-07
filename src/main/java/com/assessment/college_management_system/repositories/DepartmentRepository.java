package com.assessment.college_management_system.repositories;

import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.dto.*;
import com.assessment.college_management_system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{

    @Query("SELECT new com.assessment.college_management_system.dto.FacultyInDepartment(f.name,f.id,f.designation,f.subject,d.name) FROM Department d JOIN d.faculty f WHERE f.designation='hod'")
    public List<FacultyInDepartment> findHod();

    @Query("SELECT new com.assessment.college_management_system.dto.FacultyInDepartment(f.name,f.id,f.designation,f.subject,d.name) FROM Department d JOIN d.faculty f WHERE f.designation=:designation AND d.id=:id")
    public Optional<FacultyInDepartment> findHodExists(String designation, long id);

}

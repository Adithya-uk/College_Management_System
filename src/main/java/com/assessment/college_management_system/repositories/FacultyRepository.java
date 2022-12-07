package com.assessment.college_management_system.repositories;

import com.assessment.college_management_system.dto.FacultyCount;
import com.assessment.college_management_system.dto.FacultyInDepartment;
import com.assessment.college_management_system.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    @Query("SELECT new com.assessment.college_management_system.dto.FacultyInDepartment(f.name,f.id,f.designation,f.subject,d.name) FROM Department d JOIN d.faculty f WHERE d.name=:name")
    public List<FacultyInDepartment> getFacultyInDepartment(String name);

    @Query("SELECT new com.assessment.college_management_system.dto.FacultyCount(d.id,d.name,count(f.name),f.designation) FROM Department d JOIN d.faculty f WHERE f.designation=:designation OR (f.designation LIKE '%' AND :designation='ALL') GROUP BY d.id,f.designation")
    public List<FacultyCount>findFacultyCount(String designation);


    @Query("SELECT new com.assessment.college_management_system.dto.FacultyInDepartment(f.name,f.id,f.designation,f.subject,d.name) FROM Department d JOIN d.faculty f WHERE f.designation=:designation AND d.id=:id")
    public Optional<FacultyInDepartment> findHodExists(String designation, long id);

}

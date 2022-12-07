package com.assessment.college_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="faculty")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="designation")
    private String designation;

    @Column(name="subject")
    private String subject;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;




}

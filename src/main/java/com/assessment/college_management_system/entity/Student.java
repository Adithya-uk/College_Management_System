package com.assessment.college_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="student")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="mark_1")
    private int mark1;

    @Column(name="mark_2")
    private int mark2;

    @Column(name="mark_3")
    private int mark3;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}

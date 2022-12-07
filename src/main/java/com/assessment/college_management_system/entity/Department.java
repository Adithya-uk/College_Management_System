package com.assessment.college_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;


    @Column(name="name")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @JsonManagedReference
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private List<Faculty> faculty;

    @JsonManagedReference
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private List<Student> student;

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department( String name) {
        this.id = id;
        this.name = name;
    }

    public Department( String name, College college) {
        this.name = name;
        this.college = college;
    }

    public Department(long id, String name, College college) {
        this.id = id;
        this.name = name;
        this.college = college;
    }
}

package com.assessment.college_management_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="college")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;


    @JsonManagedReference
    @OneToMany(mappedBy = "college",cascade = CascadeType.ALL)
    private List<Department> department = new ArrayList<>();


    public College(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

package com.demo.person.persistence.entity;

import com.demo.person.service.dto.SystemPersonStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person", uniqueConstraints = {@UniqueConstraint(columnNames = "personId")})
@Data
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String personId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private int age;

    @Column
    private String email;

    @Column
    private String country;

    @Enumerated(EnumType.STRING)
    @Column
    private SystemPersonStatus status;
}

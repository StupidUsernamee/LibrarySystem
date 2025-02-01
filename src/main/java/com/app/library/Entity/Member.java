package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 10)
    private String nationalId;

    private String email;

    @Column(nullable = false, length = 11)
    private String mobilePhone;

    @Column(nullable = false, length = 2)
    private Integer age;

    private String education;

    @Column(nullable = false)
    private Boolean gender;

    @ManyToOne
    private MemberType member_type;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

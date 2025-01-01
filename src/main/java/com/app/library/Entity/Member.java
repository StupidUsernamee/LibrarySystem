package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String fullName;

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
    private Member_type member_type;

    @Column(nullable = false)
    private LocalDate joinDate;
}

package com.app.library.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String nationality;

    @Column(nullable = false)
    private String gender;

    private String email;

    private String phone;

    private String address;

    @Column(nullable = false)
    private Boolean isTranslator = false;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

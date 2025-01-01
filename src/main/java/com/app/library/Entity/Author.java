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
    private String fullName;

    private String Nationality;

    @Column(nullable = false)
    private String Gender;

    private String Email;

    private String Phone;

    private String Address;

    @Column(nullable = false)
    private Boolean isTranslator = false;
}

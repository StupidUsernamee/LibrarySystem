package com.app.library.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false, unique = true)
    private Long libraryNumber;

    @Column(nullable = true)
    private String address; //TODO: might replace with address table relation

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "hall_number")
    private Long hallNumber;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(nullable = true) // TODO: change nullable value to false after debugging
    private Library library;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}


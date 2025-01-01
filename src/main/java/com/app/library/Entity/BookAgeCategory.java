package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_age_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAgeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Integer startAge;

    @Column(nullable = false)
    private Integer endAge;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

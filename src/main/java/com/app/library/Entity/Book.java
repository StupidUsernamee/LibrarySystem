package com.app.library.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany
    private List<Book_Author> authorBook; // TODO: Fix it later if turns out wrong

    @Column(nullable = false)
    private String pageCount;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Integer publishYear;

    @Column(nullable = false)
    private String language;

    @ManyToOne
    private Genre genre;

    @ManyToOne
    private BookKind kindOfBook;

    @ManyToOne
    private BookAgeCategory ageCategory;

    @ManyToOne
    private Row row;

    @Column(nullable = true)
    private String commentId;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false, unique = true)
    private Integer bookNumber;


}

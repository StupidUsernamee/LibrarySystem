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

    @ManyToOne
    private Author author;

    @Column(nullable = false)
    private Integer pageCount;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Integer publishYear;

    @Column(nullable = false)
    private String language;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BookKind kindOfBook;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BookAgeCategory ageCategory;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Row row;

    @Column(nullable = true)
    private String commentId;  // chane this to comment relation

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false, unique = true)
    private Integer bookNumber;

    @Column(nullable = false, unique = true)
    private String bookCode;

    @OneToOne
    @JoinColumn(nullable = false)
    private BookStatus bookStatus;

}

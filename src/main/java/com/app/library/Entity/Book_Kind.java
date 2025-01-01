package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_kind")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book_Kind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

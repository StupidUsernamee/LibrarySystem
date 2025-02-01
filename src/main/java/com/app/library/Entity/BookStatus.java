package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
/*
    This class/entity may have some changes
    and some relations to requested, borrowed
    and reserved attributes.
 */
public class BookStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean available = true;

    @Column(nullable = false)
    private Boolean missing = false;

    @Column(nullable = false)
    private Boolean borrowed = false;

    @Column(nullable = false)
    private Boolean requested = false;

    @Column(nullable = false)
    private Boolean reserved = false;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

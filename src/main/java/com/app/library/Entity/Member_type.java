package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Integer penaltyRate;

    @Column(nullable = false)
    private Integer maxBorrowCount;

    @Column(nullable = false)
    private Integer maxBorrowTime;
}


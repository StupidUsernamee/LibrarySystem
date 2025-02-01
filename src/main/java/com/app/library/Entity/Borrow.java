package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = true)
    private LocalDate returnDate;

    private Integer penalty = 0;
}

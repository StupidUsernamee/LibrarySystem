package com.app.library.Entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Row {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer Capacity;

    @Column(nullable = false)
    private Integer occupiedCapacity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Shelf shelf;

    @Column(nullable = false)
    private Boolean isDeleted = false;


}

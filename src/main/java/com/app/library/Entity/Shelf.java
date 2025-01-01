package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer shelfNumber;

    @Column(nullable = false)
    private Integer shelfCapacity;

    @Column(nullable = false)
    private Integer OccupiedShelfCapacity = 0;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Hall hall;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}

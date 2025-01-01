package com.app.library.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 4000)
    @Nationalized
    private String comment_text;

    @Column(nullable = true)
    private Boolean comment_rate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

}

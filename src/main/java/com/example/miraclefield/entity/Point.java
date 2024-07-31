package com.example.miraclefield.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount = 5L;

    @OneToOne(mappedBy = "point")
    private User user;
}

package com.example.miracle_field.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotBlank(message = "Answer is required")
    private String answer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<GameHistory> gameHistories;
}
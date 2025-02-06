package com.wordlibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Word is required")
    private String word;

    @NotBlank(message = "Definition is required")
    private String definition;

    @Column(nullable = true)
    private String language;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private WordSet wordSet;
}


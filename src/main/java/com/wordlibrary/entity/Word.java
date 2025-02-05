package com.wordlibrary.entity;

import jakarta.persistence.*;
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

    private String word;

    private String definition;

    @Column(nullable = true)
    private String language;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private WordSet wordSet;
}


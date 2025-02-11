package com.wordlibrary.repository;

import com.wordlibrary.entity.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordSetRepository extends JpaRepository<WordSet, Long> {
    List<WordSet> findByUserId(Long userId);

    @Query(value = "SELECT * FROM word_sets", nativeQuery = true)
    List<WordSet> getWordSetsPublic();
}

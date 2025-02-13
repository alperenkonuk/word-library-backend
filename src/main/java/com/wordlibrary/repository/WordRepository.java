package com.wordlibrary.repository;

import com.wordlibrary.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query(value = "SELECT w FROM Word w WHERE w.wordSet.id = :set_id")
    List<Word> findByWordSetId(@Param("set_id") Long set_id);

    @Query(value = "SELECT w FROM Word w WHERE w.wordSet.id = :set_id ORDER BY RANDOM()")
    List<Word> findByWordSetIdOrderByRandom(@Param("set_id") Long set_id);

    @Query(value = "SELECT w FROM Word w WHERE w.wordSet.id = :set_id ORDER BY w.word ASC")
    List<Word> findByWordSetIdOrderAlphabeticalAsc(@Param("set_id") Long set_id);

    @Query(value = "SELECT w FROM Word w WHERE w.wordSet.id = :set_id ORDER BY w.word DESC")
    List<Word> findByWordSetIdOrderAlphabeticalDesc(@Param("set_id") Long set_id);
}

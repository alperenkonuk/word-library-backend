package com.wordlibrary.repository;

import com.wordlibrary.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByWordSetId(Long setId);

    @Query(value = "SELECT * FROM words WHERE set_id = :setId ORDER BY RANDOM()", nativeQuery = true)
    List<Word> findByWordSetIdOrderByRandom(@Param("setId") Long setId);
}

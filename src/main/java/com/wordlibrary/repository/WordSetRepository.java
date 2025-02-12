package com.wordlibrary.repository;

import com.wordlibrary.entity.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordSetRepository extends JpaRepository<WordSet, Long> {
    @Query(value = "SELECT ws FROM WordSet ws WHERE ws.user = :userId")
    List<WordSet> findByUserId(Long userId);

    @Query(value = "SELECT ws FROM WordSet ws WHERE ws.isPublic = true")
    List<WordSet> getWordSetsPublic();

    @Query(value = "SELECT ws FROM WordSet ws WHERE ws.name LIKE LOWER(CONCAT('%', :value, '%'))")
    List<WordSet> findWordSetByNameValue(@Param("value") String value);

    @Query("SELECT ws FROM WordSet ws WHERE ws.isPublic = true AND " +
            "(:name IS NULL OR LOWER(ws.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:language IS NULL OR LOWER(ws.language) LIKE LOWER(CONCAT('%', :language, '%')))")
    List<WordSet> filterWordSets(@Param("name") String name,@Param("language") String language);
}

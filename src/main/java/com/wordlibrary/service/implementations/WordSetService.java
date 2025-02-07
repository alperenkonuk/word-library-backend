package com.wordlibrary.service.implementations;

import com.wordlibrary.entity.User;
import com.wordlibrary.entity.WordSet;
import com.wordlibrary.repository.WordSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordSetService {
    private final WordSetRepository wordSetRepository;

    public List<WordSet> getAllWordSets() {
        return wordSetRepository.findAll();
    }

    public List<WordSet> getWordSetsByUser(Long userId) {
        return wordSetRepository.findByUserId(userId);
    }

    public Optional<WordSet> getWordSetById(Long id) {
        return wordSetRepository.findById(id);
    }

    public WordSet createWordSet(WordSet wordSet, User user) {
        wordSet.setUser(user);
        return wordSetRepository.save(wordSet);
    }
}

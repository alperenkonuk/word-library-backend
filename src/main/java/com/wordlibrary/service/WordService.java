package com.wordlibrary.service;

import com.wordlibrary.entity.Word;
import com.wordlibrary.entity.WordSet;
import com.wordlibrary.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    public List<Word> getWordsBySetId(Long setId){return wordRepository.findByWordSetId(setId);}

    public Optional<Word> getWordById(Long id) {
        return wordRepository.findById(id);
    }

    public Word addWordToSet(Word word, WordSet wordSet) {
        word.setWordSet(wordSet);
        return wordRepository.save(word);
    }
}

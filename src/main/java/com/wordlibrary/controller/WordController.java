package com.wordlibrary.controller;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordDto;
import com.wordlibrary.service.interfaces.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sets/{set_id}/words")
public class WordController {

    private final WordService wordService;

    @PostMapping
    public ResponseEntity<Response> addWord(@PathVariable("set_id") Long setId, @RequestBody WordDto wordDto) {
        return ResponseEntity.ok(wordService.addWord(wordDto, setId));
    }

    @DeleteMapping("/{word_id}")
    public ResponseEntity<Response> deleteWord(@PathVariable("word_id") Long wordId, @PathVariable String set_id) {
        return ResponseEntity.ok(wordService.deleteWord(wordId));
    }

    @PutMapping("/{word_id}")
    public ResponseEntity<Response> updateWord(@PathVariable("set_id") Long setId, @PathVariable("word_id") Long wordId, @RequestBody WordDto wordDto) {
        return ResponseEntity.ok(wordService.updateWord(wordDto, wordId));
    }

    @GetMapping("/{word_id}")
    public ResponseEntity<Response> getWord(@PathVariable("set_id") Long setId, @PathVariable("word_id") Long wordId) {
        return ResponseEntity.ok(wordService.getWord(wordId));
    }

    @GetMapping
    public ResponseEntity<Response> getWordsByWordSet(@PathVariable("set_id") Long setId) {
        return ResponseEntity.ok(wordService.getWordsBySetId(setId));
    }
}


package com.wordlibrary.controller;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordSetDto;
import com.wordlibrary.service.interfaces.WordSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sets")
@RequiredArgsConstructor
public class WordSetController {

    private final WordSetService wordSetService;

    @PostMapping
    public ResponseEntity<Response> createWordSet(@RequestBody WordSetDto wordSetDto) {
        return ResponseEntity.ok(wordSetService.addWordSet(wordSetDto));
    }

    @GetMapping("/{set_id}")
    public ResponseEntity<Response> getWordSet(@PathVariable Long set_id) {
        return ResponseEntity.ok(wordSetService.getWordSet(set_id));
    }

    @PutMapping("/{set_id}")
    public ResponseEntity<Response> updateWordSet(@PathVariable Long set_id, @RequestBody WordSetDto wordSetDto) {
        wordSetDto.setId(set_id);
        return ResponseEntity.ok(wordSetService.updateWordSet(wordSetDto));
    }

    @DeleteMapping("/{set_id}")
    public ResponseEntity<Response> deleteWordSet(@PathVariable Long set_id) {
        return ResponseEntity.ok(wordSetService.deleteWordSet(set_id));
    }

    @GetMapping("/user")
    public ResponseEntity<Response> getUserWordSets() {
        return ResponseEntity.ok(wordSetService.getWordSetsByUser());
    }

    @GetMapping("/public")
    public ResponseEntity<Response> getPublicWordSets() {
        return ResponseEntity.ok(wordSetService.getPublicWordSets());
    }
}

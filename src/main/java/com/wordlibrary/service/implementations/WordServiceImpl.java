package com.wordlibrary.service.implementations;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordDto;
import com.wordlibrary.entity.Word;
import com.wordlibrary.entity.WordSet;
import com.wordlibrary.exception.NotFoundException;
import com.wordlibrary.mapper.EntityDtoMapper;
import com.wordlibrary.repository.WordRepository;
import com.wordlibrary.repository.WordSetRepository;
import com.wordlibrary.service.interfaces.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final WordSetRepository wordSetRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response addWord(WordDto wordDto) {
        WordSet wordSet = wordSetRepository.findById(wordDto.getSetId())
                .orElseThrow(() -> new NotFoundException("Word set not found"));

        Word word = entityDtoMapper.mapDtoToWord(wordDto, wordSet);

        Word savedWord = wordRepository.save(word);

        return Response.builder()
                .status(200)
                .message("Word added succesfully")
                .word(entityDtoMapper.mapWordToDtoBasic(savedWord))
                .build();
    }


    @Override
    public Response updateWord(WordDto wordDto) {
        Word word = wordRepository.findById(wordDto.getId())
                .orElseThrow(() -> new NotFoundException("Word not found"));

        if (wordDto.getWord() != null) word.setWord(wordDto.getWord());
        if (wordDto.getDefinition() != null) word.setDefinition(wordDto.getDefinition());

        Word updatedWord = wordRepository.save(word);

        return Response.builder()
                .status(200)
                .message("Word updated successfully")
                .word(entityDtoMapper.mapWordToDtoBasic(updatedWord))
                .build();
    }

    @Override
    public Response getWord(Long id) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Word Not found"));

        return Response.builder()
                .status(200)
                .message("Word transmitted successfully")
                .word(entityDtoMapper.mapWordToDtoBasic(word))
                .build();
    }

    @Override
    public Response deleteWord(Long id) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Word not found"));

        wordRepository.delete(word);

        return Response.builder()
                .status(200)
                .message("Word deleted successfully")
                .build();
    }
}

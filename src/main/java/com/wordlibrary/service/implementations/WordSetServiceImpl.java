package com.wordlibrary.service.implementations;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordDto;
import com.wordlibrary.dto.WordSetDto;
import com.wordlibrary.entity.User;
import com.wordlibrary.entity.WordSet;
import com.wordlibrary.exception.NotFoundException;
import com.wordlibrary.mapper.EntityDtoMapper;
import com.wordlibrary.repository.WordRepository;
import com.wordlibrary.repository.WordSetRepository;
import com.wordlibrary.service.interfaces.UserService;
import com.wordlibrary.service.interfaces.WordSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordSetServiceImpl implements WordSetService {

    private final WordSetRepository wordSetRepository;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;
    private final WordRepository wordRepository;


    @Override
    public Response addWordSet(WordSetDto wordSetDto) {
        User user = userService.getLoginUser();

        WordSet wordSet = entityDtoMapper.mapDtoToWordSet(wordSetDto, user);

        WordSet savedWordSet = wordSetRepository.save(wordSet);

        return Response.builder()
                .status(200)
                .message("WordSet created successfully")
                .wordSet(entityDtoMapper.mapWordSetToDtoBasic(savedWordSet))
                .build();
    }

    @Override
    public Response deleteWordSet(Long id) {
        WordSet wordSet = wordSetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WordSet not found"));

        wordSetRepository.delete(wordSet);

        return Response.builder()
                .status(200)
                .message("WordSet deleted successfully")
                .build();
    }

    @Override
    public Response updateWordSet(WordSetDto wordSetDto) {
        WordSet wordSet = wordSetRepository.findById(wordSetDto.getId())
                .orElseThrow(() -> new NotFoundException("Word not found"));

        if (wordSetDto.getName() != null) wordSet.setName(wordSetDto.getName());
        if (wordSetDto.getIsPublic() != null) wordSet.setIsPublic(wordSetDto.getIsPublic());

        WordSet updatedWordSet = wordSetRepository.save(wordSet);

        return Response.builder()
                .status(200)
                .message("WordSet updated successfully")
                .wordSet(entityDtoMapper.mapWordSetToDtoBasic(updatedWordSet))
                .build();
    }

    @Override
    public Response getWordSet(Long id) {
        WordSet wordSet = wordSetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Word not found"));

        return Response.builder()
                .status(200)
                .message("WordSet transmitted successfully")
                .wordSet(entityDtoMapper.mapWordSetToDtoBasic(wordSet))
                .build();
    }

    @Override
    public Response getWordSetsByUser() {
        User user = userService.getLoginUser();

        List<WordSetDto> wordSetDtoList = wordSetRepository.findByUserId(user.getId())
                .stream()
                .map(entityDtoMapper::mapWordSetToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("WordSets transmitted successfully")
                .wordSetList(wordSetDtoList)
                .build();
    }

    @Override
    public Response getPublicWordSets() {
        List<WordSetDto> wordSetList = wordSetRepository.getWordSetsPublic()
                .stream()
                .map(entityDtoMapper::mapWordSetToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("Public wordsets transmitted successfully")
                .wordSetList(wordSetList)
                .build();
    }



    @Override
    public Response shuffleWords(Long setId) {
        List<WordDto> shuffledWordList = wordRepository.findByWordSetIdOrderByRandom(setId)
                .stream()
                .map(entityDtoMapper::mapWordToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("WordSet shuffled successfully")
                .wordList(shuffledWordList)
                .build();
    }

    @Override
    public Response searchForSets(String name) {
        List<WordSetDto> wordSetDtoList = wordSetRepository.findWordSetByNameValue(name)
                .stream()
                .map(entityDtoMapper::mapWordSetToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("WordSets transmitted successfully")
                .wordSetList(wordSetDtoList)
                .build();
    }

    @Override
    public Response filterWordSets(String name, String language) {
        List<WordSetDto> wordSetDtoList = wordSetRepository.filterWordSets(name, language)
                .stream()
                .map(entityDtoMapper::mapWordSetToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("Wordsets filtered successfully")
                .wordSetList(wordSetDtoList)
                .build();
    }

}

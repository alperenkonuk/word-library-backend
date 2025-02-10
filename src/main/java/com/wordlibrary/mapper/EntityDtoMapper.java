package com.wordlibrary.mapper;

import com.wordlibrary.dto.UserDto;
import com.wordlibrary.dto.WordDto;
import com.wordlibrary.dto.WordSetDto;
import com.wordlibrary.entity.User;
import com.wordlibrary.entity.Word;
import com.wordlibrary.entity.WordSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityDtoMapper {

    public UserDto mapUserToDtoBasic(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setStreak(user.getStreak());

        return userDto;
    }

    public WordDto mapWordToDtoBasic(Word word) {
        WordDto wordDto = new WordDto();
        wordDto.setId(word.getId());
        wordDto.setWord(word.getWord());
        wordDto.setDefinition(word.getDefinition());
        wordDto.setSetId(word.getWordSet().getId());

        return wordDto;
    }

    public Word mapDtoToWord(WordDto wordDto, WordSet wordSet) {
        Word word = new Word();

        word.setWord(wordDto.getWord());
        word.setDefinition(wordDto.getDefinition());
        word.setWordSet(wordSet);

        return word;
    }

    public WordSetDto mapWordSetToDtoBasic(WordSet wordSet) {
        WordSetDto wordSetDto = new WordSetDto();
        wordSetDto.setId(wordSet.getId());
        wordSetDto.setName(wordSet.getName());
        wordSetDto.setLanguage(wordSet.getLanguage());

        return wordSetDto;
    }

    public WordSet mapDtoToWordSet(WordSetDto wordSetDto, User user) {
        WordSet wordSet = new WordSet();

        wordSet.setName(wordSetDto.getName());
        wordSet.setIsPublic(wordSetDto.getIsPublic());
        wordSet.setLanguage(wordSetDto.getLanguage());
        wordSet.setUser(user);

        return wordSet;
    }

    public WordSetDto mapWordSetToDtoPlusWords(WordSet wordSet) {
        WordSetDto wordSetDto = mapWordSetToDtoBasic(wordSet);

        if (wordSet.getWords() != null) {
            List<WordDto> wordDtos = wordSet.getWords()
                    .stream()
                    .map(this::mapWordToDtoBasic)
                    .toList();
            wordSetDto.setWords(wordDtos);
        }

        return wordSetDto;
    }

    public UserDto mapUserToDtoPlusWordSets(User user) {
        UserDto userDto = mapUserToDtoBasic(user);

        if (user.getWordSets() != null) {
            List<WordSetDto> wordSetDtos = user.getWordSets()
                    .stream()
                    .map(this::mapWordSetToDtoBasic)
                    .toList();
            userDto.setWordSets(wordSetDtos);
        }

        return userDto;
    }
}

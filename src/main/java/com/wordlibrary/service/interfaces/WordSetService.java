package com.wordlibrary.service.interfaces;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordSetDto;

public interface WordSetService {
    Response addWordSet(WordSetDto wordSetDto);

    Response deleteWordSet(Long id);

    Response updateWordSet(WordSetDto wordSetDto);

    Response getWordSet(Long id);

    Response getWordSetsByUser();

    Response getPublicWordSets();

    Response shuffleWords(Long id);
}

package com.wordlibrary.service.interfaces;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordDto;

public interface WordService {
    Response addWord(WordDto wordDto, Long setId);

    Response updateWord(WordDto wordDto, Long wordId);

    Response getWord(Long id);

    Response getWordsBySetId(Long setId);

    Response deleteWord(Long id);
}
package com.wordlibrary.service.interfaces;

import com.wordlibrary.dto.Response;
import com.wordlibrary.dto.WordDto;

public interface WordService {
    Response addWord(WordDto wordDto);

    Response updateWord(WordDto wordDto);

    Response getWord(Long id);

    Response deleteWord(Long id);
}
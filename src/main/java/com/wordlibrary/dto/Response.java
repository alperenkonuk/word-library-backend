package com.wordlibrary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int status;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private String accessToken;
    private String refreshToken;
    private String expirationTime;

    private UserDto user;
    private List<UserDto> userList;

    private WordDto word;
    private List<WordDto> wordList;

    private WordSetDto wordSet;
    private List<WordSetDto> wordSetList;

}

package com.attebjorner.hebara.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SentenceDto
{
    private long id;

    private String originalSentence;

//    TODO
//    private String translation;

//    @JsonProperty("words_positions")
//    private List<Integer> requestedWordsPositions;
//
//    @JsonProperty("words_ids")
//    private List<Long> wordsIds;

    public SentenceDto(long id, String originalSentence)
    {
        this.id = id;
        this.originalSentence = originalSentence;
//        this.translation = translation;
//        this.requestedWordsPositions = requestedWordsPositions;
//        this.wordsIds = wordsIds;
    }

    public long getId()
    {
        return id;
    }

    public String getOriginalSentence()
    {
        return originalSentence;
    }

//    public String getTranslation()
//    {
//        return translation;
//    }
//
//    public List<Integer> getRequestedWordsPositions()
//    {
//        return requestedWordsPositions;
//    }
//
//    public List<Long> getWordsIds()
//    {
//        return wordsIds;
//    }
}

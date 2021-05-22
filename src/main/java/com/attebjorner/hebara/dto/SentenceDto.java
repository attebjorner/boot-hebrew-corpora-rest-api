package com.attebjorner.hebara.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SentenceDto
{
    private long id;

    private String originalSentence;

    private String translation;

    @JsonProperty("words_positions")
    private List<Integer> requestedWordsPositions;

    @JsonProperty("words_ids")
    private List<Long> wordsIds;

    public SentenceDto()
    {
    }

    public SentenceDto(long id, String originalSentence, String translation, List<Integer> requestedWordsPositions, List<Long> wordsIds)
    {
        this.id = id;
        this.originalSentence = originalSentence;
        this.translation = translation;
        this.requestedWordsPositions = requestedWordsPositions;
        this.wordsIds = wordsIds;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getOriginalSentence()
    {
        return originalSentence;
    }

    public void setOriginalSentence(String originalSentence)
    {
        this.originalSentence = originalSentence;
    }

    public String getTranslation()
    {
        return translation;
    }

    public void setTranslation(String translation)
    {
        this.translation = translation;
    }

    public List<Integer> getRequestedWordsPositions()
    {
        return requestedWordsPositions;
    }

    public void setRequestedWordsPositions(List<Integer> requestedWordsPositions)
    {
        this.requestedWordsPositions = requestedWordsPositions;
    }

    public List<Long> getWordsIds()
    {
        return wordsIds;
    }

    public void setWordsIds(List<Long> wordsIds)
    {
        this.wordsIds = wordsIds;
    }
}

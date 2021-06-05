package com.attebjorner.hebara.dto;

public class SentenceDto
{
    private long id;

    private String originalSentence;

    public SentenceDto(long id, String originalSentence)
    {
        this.id = id;
        this.originalSentence = originalSentence;
    }

    public long getId()
    {
        return id;
    }

    public String getOriginalSentence()
    {
        return originalSentence;
    }
}

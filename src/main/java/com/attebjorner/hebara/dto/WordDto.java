package com.attebjorner.hebara.dto;

import java.util.Map;

public class WordDto
{
    private String word;

    private String lemma;

    private String pos;

    private Map<String, String> gram;

    public WordDto(String word, String lemma, String pos, Map<String, String> gram)
    {
        this.word = word;
        this.lemma = lemma;
        this.pos = pos;
        this.gram = gram;
    }

    public String getWord()
    {
        return word;
    }

    public String getLemma()
    {
        return lemma;
    }

    public String getPos()
    {
        return pos;
    }

    public Map<String, String> getGram()
    {
        return gram;
    }
}

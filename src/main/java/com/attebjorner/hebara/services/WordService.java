package com.attebjorner.hebara.services;

import com.attebjorner.hebara.dao.WordDAO;
import com.attebjorner.hebara.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class WordService
{
    private final WordDAO wordDAO;

    @Autowired
    public WordService(WordDAO wordDAO)
    {
        this.wordDAO = wordDAO;
    }

    public Set<Word> getByLemma(String lemma)
    {
        return wordDAO.getByLemma(lemma);
    }
}

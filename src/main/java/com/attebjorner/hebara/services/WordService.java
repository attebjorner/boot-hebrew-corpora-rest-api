package com.attebjorner.hebara.services;

import com.attebjorner.hebara.dao.WordDao;
import com.attebjorner.hebara.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WordService
{
    private final WordDao wordDAO;

    @Autowired
    public WordService(WordDao wordDAO)
    {
        this.wordDAO = wordDAO;
    }

    public Set<Word> getByLemma(String lemma)
    {
        return wordDAO.getByLemma(lemma);
    }
}
